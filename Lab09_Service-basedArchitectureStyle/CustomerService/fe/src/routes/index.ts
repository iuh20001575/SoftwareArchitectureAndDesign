import { routes } from '../configs';
import { AuthLayout } from '../layouts';
import { Home, Login, NotFound, Register } from '../pages';

export const publicRoutes = [
    {
        path: routes.home,
        element: Home,
    },
    {
        path: routes.register,
        element: Register,
        layout: AuthLayout,
    },
    {
        path: routes.login,
        element: Login,
        layout: AuthLayout,
    },
    {
        path: routes.notFound,
        element: NotFound,
    },
];
