export default function LoginApp(props) {
    const fetcher = props.fetcher;
    return (
        <div className="container">
            <table id="input-table">
                <tbody>
                <tr>
                    <td colSpan="3" className="input-cell-l">Войди или зарегистрируйся:</td>
                </tr>

                <tr>
                    <td className="input-cell-l">Логин:</td>
                    <td>
                        <input className="input-select rounded box" name="login" placeholder="Enter login"
                               required type="text" onChange={fetcher.loginChangeHandle}/>
                    </td>
                </tr>
                <tr>
                    <td className="input-cell-l">Пароль:</td>
                    <td>
                        <input className="input-select rounded box" name="password" placeholder="Enter password"
                               required type="password" onChange={fetcher.passwordChangeHandle}/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <button className="box rounded redirect"
                                onClick={fetcher.loginHandle}>Войти</button>
                    </td>
                    <td>
                        <button className="box rounded redirect"
                                onClick={fetcher.registerHandle}>Зарегистрироваться</button>
                    </td>
                </tr>
                </tbody>
            </table>

        </div>
    );
}
