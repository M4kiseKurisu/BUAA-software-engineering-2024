export function input_check(content, type, title) {
    if (content.length === 0) {
        return title + "不能为空！";
    }

    switch (type) {
        case "username_create":
            return username_check(content);
        case "email":
            return email_check(content);
        case "tel":
            return tel_check(content);
        default:
            return true;
    }
}

function username_check(content) {
    if (content.length > 8) {
        return "用户名过长！"
    } else {
        return true;
    }
}

function email_check(content) {
    var string_reg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
    return string_reg.test(content) ? true : "输入邮箱格式不合法！";
}

function tel_check(content) {
    var tel_reg = /^1[3-9][0-9]{9}$/;
    return tel_reg.test(content) ? true : "输入的手机号不合法！";
}