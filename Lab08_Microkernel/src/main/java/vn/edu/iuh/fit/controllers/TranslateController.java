package vn.edu.iuh.fit.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.edu.iuh.fit.Language;
import vn.edu.iuh.fit.PluginManager;
import vn.edu.iuh.fit.models.Word;

import java.util.concurrent.atomic.AtomicReference;

@Controller
@RequestMapping("/")
public class TranslateController {
    @GetMapping({"", "/"})
    public String index(Model model) {
        model.addAttribute("word", new Word());
        model.addAttribute("language", "en-vn");

        return "index";
    }

    @PostMapping("/translate")
    public String translate(Model model, @RequestParam String language, @RequestParam String word) {
        AtomicReference<Word> result = new AtomicReference<>(new Word());

        PluginManager.PLUGINS.values().forEach(clazz -> {
            if (clazz.name().equalsIgnoreCase(language)) {
                Word lookup = ((Language) clazz).lookup(word);
                System.out.println("lookup: "+ lookup);

                if (lookup != null) {
                    result.set(lookup);
                } else {
                    result.get().setWord(word);
                }
            }
        });

        model.addAttribute("word", result.get());
        model.addAttribute("language", language);
        System.out.println(result.get());

        return "index";
    }
}
