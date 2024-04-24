import {
    CalendarMonth,
    LockOutlined,
    Person,
    Phone,
} from '@mui/icons-material';
import classNames from 'classnames/bind';
import FormGroup from '../../components/formGroup';
import styles from './styles.module.scss';

const cx = classNames.bind(styles);

const Register = () => {
    return (
        <>
            <div className={cx('signup-form')}>
                <h2 className={cx('form-title')}>Sign up</h2>
                <form className={cx('register-form')}>
                    <FormGroup
                        Icon={Person}
                        name='name'
                        placeholder='Your name'
                    />
                    <FormGroup
                        Icon={Phone}
                        name='phone'
                        placeholder='Your phone'
                        type='tel'
                    />
                    <FormGroup
                        Icon={CalendarMonth}
                        name='dob'
                        placeholder='Your birthday'
                        type='date'
                    />
                    <FormGroup
                        Icon={LockOutlined}
                        name='password'
                        placeholder='Your password'
                        type='password'
                    />
                    <div className={cx('form-group')}>
                        <input
                            type='checkbox'
                            name='agree-term'
                            id='agree-term'
                            className={cx('agree-term')}
                        />
                        <label
                            htmlFor='agree-term'
                            className={cx('label-agree-term')}
                        >
                            <span>
                                <span></span>
                            </span>
                            I agree all statements in{' '}
                            <a href='#' className={cx('term-service')}>
                                Terms of service
                            </a>
                        </label>
                    </div>
                    <div className={cx('form-group', 'form-button')}>
                        <input
                            type='submit'
                            name='signup'
                            id='signup'
                            className={cx('form-submit')}
                            value='Register'
                        />
                    </div>
                </form>
            </div>
            <div className={cx('signup-image')}>
                <figure className={cx('figure')}>
                    <img src='signup-image.jpg' alt='sing up image' />
                </figure>
                <a href='#' className={cx('signup-image-link')}>
                    I am already member
                </a>
            </div>
        </>
    );
};

export default Register;
