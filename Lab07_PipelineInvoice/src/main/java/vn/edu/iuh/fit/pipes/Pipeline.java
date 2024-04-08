package vn.edu.iuh.fit.pipes;

import vn.edu.iuh.fit.core.entities.IFilter;
import vn.edu.iuh.fit.core.entities.IMessage;

import java.util.List;

public class Pipeline extends PipelineBase<IMessage> {
    public Pipeline(List<IFilter<IMessage>> filters) {
        filters.forEach(this::RegisterFilter);
    }
}
