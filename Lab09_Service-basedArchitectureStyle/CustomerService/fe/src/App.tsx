import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { publicRoutes } from './routes';
import { Fragment } from 'react/jsx-runtime';

function App() {
    return (
        <BrowserRouter>
            <Routes>
                {publicRoutes.map((route) => {
                    const Element = route.element;
                    const Layout = route.layout || Fragment;

                    return (
                        <Route
                            element={
                                <Layout>
                                    <Element />
                                </Layout>
                            }
                            path={route.path}
                            key={route.path}
                        />
                    );
                })}
            </Routes>
        </BrowserRouter>
    );
}

export default App;
