import classNames from 'classnames/bind';
import styles from './styles.module.scss';
import { OverridableComponent } from '@mui/material/OverridableComponent';
import { SvgIconTypeMap } from '@mui/material';
import { HTMLInputTypeAttribute, ReactNode } from 'react';

const cx = classNames.bind(styles);

const FromGroup = ({
    Icon,
    name = '',
    type = 'text',
    placeholder = '',
    label,
    value = '',
    onChangeText = () => {},
}: {
    Icon?: OverridableComponent<SvgIconTypeMap<object, 'svg'>> & {
        muiName: string;
    };
    name?: string;
    type?: HTMLInputTypeAttribute;
    placeholder?: string;
    label?: ReactNode;
    value?: string;
    onChangeText?: (value: string) => void;
}) => {
    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        onChangeText(e.target.value);
    };

    if (type === 'checkbox')
        return (
            <div className={cx('form-group')}>
                <input
                    type='checkbox'
                    name='agree-term'
                    id='agree-term'
                    className={cx('agree-term')}
                    value={value}
                    onChange={handleChange}
                />
                <label htmlFor='agree-term' className={cx('label-agree-term')}>
                    <span>
                        <span></span>
                    </span>
                    {label}
                </label>
            </div>
        );

    return (
        <div className={cx('form-group')}>
            <label htmlFor={name}>{Icon && <Icon />}</label>
            <input
                type={type}
                name={name}
                id={name}
                placeholder={placeholder}
                value={value}
                onChange={handleChange}
            />
        </div>
    );
};

export default FromGroup;
