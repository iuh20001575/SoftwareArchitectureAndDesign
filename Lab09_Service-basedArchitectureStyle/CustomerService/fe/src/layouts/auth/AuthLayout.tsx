import classNames from 'classnames/bind';
import { ReactNode } from 'react';
import styles from './styles.module.scss';

const cx = classNames.bind(styles);

const AuthLayout = ({ children }: { children: ReactNode }) => {
    return (
        <section className={cx('wrapper')}>
            <div className={cx('container')}>
                <div className={cx('content')}>{children}</div>
            </div>
        </section>
    );
};

export default AuthLayout;
