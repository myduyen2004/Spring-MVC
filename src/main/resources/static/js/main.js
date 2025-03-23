// main.js
document.addEventListener('DOMContentLoaded', function() {
    // Add User modal functionality
    var addUserBtn = document.getElementById('addUserBtn');
    var userModal = document.getElementById('userModal');
    var closeModal = document.getElementsByClassName('close')[0];

    if (addUserBtn) {
        addUserBtn.addEventListener('click', function () {
            if (userModal) {
                userModal.style.display = 'block';
            }
        });
    }
})