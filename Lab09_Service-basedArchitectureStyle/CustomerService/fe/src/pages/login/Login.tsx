import {
    Facebook,
    Google,
    LockOutlined,
    Person,
    Twitter,
} from '@mui/icons-material';
import classNames from 'classnames/bind';
import FormGroup from '../../components/formGroup';
import styles from './styles.module.scss';
import { Link } from 'react-router-dom';
import { routes } from '../../configs';
import { useState } from 'react';
import { authService } from '../../services';

const cx = classNames.bind(styles);

const Login = () => {
    const [phone, setPhone] = useState('');
    const [password, setPassword] = useState('');

    const handleLogin = async () => {
        const res = await authService.login({ phone, password });

        console.log(`res.data`, res.data);
    };

    return (
        <>
            <div className={cx('signin-image')}>
                <figure>
                    <img src='signin-image.jpg' alt='sing up image' />
                </figure>
                <Link to={routes.register} className={cx('signup-image-link')}>
                    Create an account
                </Link>
            </div>

            <div className={cx('signin-form')}>
                <h2 className={cx('form-title')}>Sign up</h2>
                <form className={cx('register-form')}>
                    <FormGroup
                        Icon={Person}
                        name='Phone'
                        placeholder='Your phone'
                        value={phone}
                        onChangeText={setPhone}
                    />
                    <FormGroup
                        Icon={LockOutlined}
                        name='password'
                        placeholder='Your password'
                        value={password}
                        onChangeText={setPassword}
                    />
                    <div className={cx('form-group')}>
                        <input
                            type='checkbox'
                            name='remember-me'
                            id='remember-me'
                            className={cx('agree-term')}
                        />
                        <label
                            htmlFor='remember-me'
                            className={cx('label-agree-term')}
                        >
                            <span>
                                <span></span>
                            </span>
                            Remember me
                        </label>
                    </div>
                    <div className={cx('form-group', 'form-button')}>
                        <button
                            onClick={handleLogin}
                            className={cx('form-submit')}
                            type='button'
                        >
                            Login
                        </button>
                    </div>
                </form>
                <div className={cx('social-login')}>
                    <span className={cx('social-label')}>Or login with</span>
                    <ul className={cx('socials')}>
                        <li>
                            <a href='#'>
                                <span className={cx('social', 'zmdi-facebook')}>
                                    <Facebook />
                                </span>
                            </a>
                        </li>
                        <li>
                            <a href='#'>
                                <span className={cx('social', 'zmdi-twitter')}>
                                    <Twitter />
                                </span>
                            </a>
                        </li>
                        <li>
                            <a href='#'>
                                <span className={cx('social', 'zmdi-google')}>
                                    <Google />
                                </span>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </>
    );
};

export default Login;
