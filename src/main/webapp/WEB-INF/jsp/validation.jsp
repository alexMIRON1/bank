<script>
function validateRegister(){
    if(document.getElementById("password").value !==document.getElementById("confirm_password").value){
        document.getElementById('messageConfirm').style.color = 'red';
        document.getElementById('messageConfirm').innerHTML = 'passwords are not same';
        document.getElementById('button').disabled = true;
    }else {
        document.getElementById('messageConfirm').style.color = 'green';
        document.getElementById('messageConfirm').innerHTML = 'correct'
        document.getElementById('button').disabled = false;
    }
    let el = document.getElementById('login').value;
    if(el.match('^[a-zA-Zа-яА-Я]{3,30}$')){
        document.getElementById('messageLogin').innerHTML = '';
        document.getElementById('button').disabled = false;
    }else {
        document.getElementById('messageLogin').style.color = 'red';
        document.getElementById('messageLogin').innerHTML = 'Input only Latin or Cyrillic, 3-30 letters';
        document.getElementById('button').disabled = true;
    }
    if(document.getElementById('password').value.length >= 4 && document.getElementById('password').value.length <=16){
        document.getElementById('messagePassword').innerHTML = '';
        document.getElementById('button').disabled = false;
    }else{
        document.getElementById('messagePassword').style.color = 'red';
        document.getElementById('messagePassword').innerHTML = '4-16 symbols'
        document.getElementById('button').disabled = true;
    }
}
function matchMakePayment(balance){
    const sum = parseInt(document.getElementById('sum').value);
    if(sum <= balance || document.getElementById('sum').value===""){
        document.getElementById('messagePayment').innerHTML = '';
        document.getElementById('button').disabled = false;
        return true;
    }else{
        document.getElementById('messagePayment').style.color = 'red';
        document.getElementById('messagePayment').innerHTML = 'not enough money on balance';
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
        document.getElementById('messageTopUp').innerHTML = 'max top-up 10.000'
        document.getElementById('button').disabled = true;
        return false;
    }
}
</script>