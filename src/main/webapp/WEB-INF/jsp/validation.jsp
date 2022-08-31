<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
function validateRegister(){
    let el = document.getElementById('login').value;
    var reg = '^[a-zA-Z]{3,30}$|^[а-яА-Я]{3,30}$';
    if(el.match(reg)){
        document.getElementById('messageLogin').innerHTML = '';
        document.getElementById('button').disabled = false;
        if(document.getElementById('password').value.length >= 4 && document.getElementById('password').value.length <=16) {
            document.getElementById('messagePassword').innerHTML = '';
            document.getElementById('button').disabled = false;
            if(document.getElementById("password").value !==document.getElementById("confirm_password").value) {
                document.getElementById('messageConfirm').style.color = 'red';
                document.getElementById('messageConfirm').innerHTML = '<fmt:message key="validation.message.confirm.password"/>';
                document.getElementById('button').disabled = true;
            }else{
                document.getElementById('messageConfirm').style.color = 'green';
                document.getElementById('messageConfirm').innerHTML = '<fmt:message key="validation.message.confirm"/>';
                document.getElementById('button').disabled = false;
                return false;
            }
        }else{
            document.getElementById('messagePassword').style.color = 'red';
            document.getElementById('messagePassword').innerHTML = '<fmt:message key="validation.message.password"/>';
            document.getElementById('button').disabled = true;
            return false;
        }
    }else{
        document.getElementById('messageLogin').style.color = 'red';
        document.getElementById('messageLogin').innerHTML = '<fmt:message key="validation.message.confirm.login"/>';
        document.getElementById('button').disabled = true;
        return false;
    }
    return true;
}
function matchMakePayment(balance){
    const sum = parseInt(document.getElementById('sum').value);
    if(sum <= balance || document.getElementById('sum').value===""){
        document.getElementById('messagePayment').innerHTML = '';
        document.getElementById('button').disabled = false;
        return true;
    }else{
        document.getElementById('messagePayment').style.color = 'red';
        document.getElementById('messagePayment').innerHTML = '<fmt:message key="validation.message.make_Payment"/>';
        document.getElementById('button').disabled = true;
        return false;
    }
}
function matchTopUp(){
    const sum = parseInt(document.getElementById('top-up').value);
    if(sum<=10000 || document.getElementById('top-up').value ===""){
        document.getElementById('messageTopUp').innerHTML = '';
        document.getElementById('button').disabled = false;
        return true;
    }else{
        document.getElementById('messageTopUp').style.color = 'red';
        document.getElementById('messageTopUp').innerHTML = '<fmt:message key="validation.message.top-up"/>'
        document.getElementById('button').disabled = true;
        return false;
    }
}
function matchSetName(){
    const name = document.getElementById('customName').value;
    var reg = /^[a-zA-Z0-9 ']+$|^[а-яА-Я0-9 ']+$/
    if(name.match(reg)){
        document.getElementById('messageSetName').innerHTML = '';
        document.getElementById('buttonName').disabled = false;
        return true;
    }else {
        document.getElementById('messageSetName').style.color = 'red';
        document.getElementById('messageSetName').innerHTML = '<fmt:message key="validation.message.set_name"/>'
        document.getElementById('buttonName').disabled = true;
        return  false;
    }
}
function matchEmail(){
    const name = document.getElementById('email').value
    var reg =/^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
    if(name.match(reg)){
        document.getElementById('messageEmail').innerHTML = '';
        document.getElementById('buttonEmail').disabled = false;
        return true;
    }else {
        document.getElementById('messageEmail').style.color = 'red';
        document.getElementById('messageEmail').innerHTML = '<fmt:message key="validation.message.email"/>'
        document.getElementById('buttonEmail').disabled = true;
        return false;
    }
}
function matchPayBill(){
    const name = document.getElementById('toCard').value
    var reg = /^[0-9]{4}$/
    if(name.match(reg)){
        document.getElementById('messagePay').innerHTML = '';
        document.getElementById('buttonPay').disabled = false;
        return true;
    }else{
        document.getElementById('messagePay').style.color = 'red';
        document.getElementById('messagePay').innerHTML = '<fmt:message key="validation.message.pay"/>'
        document.getElementById('buttonPay').disabled = true;
        return false;
    }
}
</script>