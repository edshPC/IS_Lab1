const origin = "https://localhost:8080/";

export async function request(url, method = "GET", body = {}) {
    let res = {success: false};
    try {
        let raw = await fetch(origin + url, {
            method: method,
            headers: {'Content-Type': 'application/json;charset=utf-8'},
            //credentials: 'include',
            body: JSON.stringify(body)
        });
        res = await raw.json();
    } catch (e) {
        res.error = e;
    }

    console.log(res);
    return res;
}
