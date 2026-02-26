document.addEventListener('DOMContentLoaded', function() {
    const registrationForm = document.getElementById('registrationForm');
    registrationForm.addEventListener('submit', validateForm);
});
function validateForm(event) {
    const username = document.getElementById('name').value;
    const email = document.getElementById('email').value;
    const phoneNumber = document.getElementById('phone-number').value;
    const password = document.getElementById('password').value;

    // Reset error messages
    document.getElementById('nameError').innerHTML = '';
    document.getElementById('emailError').innerHTML = '';

    let isValid = true;

    if (username.length < 3) {
        document.getElementById('usernameError').innerHTML = 'Username must be at least 3 characters long.';
        isValid = false;
    }

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
        document.getElementById('emailError').innerHTML = 'Please enter a valid email address.';
        isValid = false;
    }
    const ukPhoneNumberRegex = /^(\+44|0)7\d{9}$/;
    if (!ukPhoneNumberRegex.test(phoneNumber)) {
        document.getElementById('phoneNumberError').innerHTML = 'Please enter a valid UK phone number.';
        isValid = false;
    }

    if (password.length < 8) {
        document.getElementById('passwordError').innerHTML = 'Password must be at least 8 characters long.';
        isValid = false;
    }
    if (!isValid) {
        event.preventDefault();
    }
        console.log('Validation function called. isValid:', isValid);
    return isValid;
}