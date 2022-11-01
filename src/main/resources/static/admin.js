const requestURL = 'http://localhost:8080/api/users';
const usersTableNavLink = document.getElementById("horizontal_navigation-users_table");
const allUsersTable = document.querySelector(".all-users-table");

const fillUsers = (users) => {
    if (users.length > 0) {
        let temp = '';
        users.forEach((user) => {
            temp += `
                <tr>
                    <td> ${user.id} </td>
                    <td> ${user.firstName} </td>
                    <td> ${user.lastName} </td>
                    <td> ${user.age} </td>
                    <td> ${user.email} </td>
                    <td> ${user.roles.map(role => " " + role.name.substring(5))} </td>
                    <td> <button type="button" class="btn btn-info" id="btn-edit-modal-call" data-toggle="modal" data-target="modal-edit"
                    data-id="${user.id}">Edit</button></td>
                    <td> <button type="button" class="btn btn-danger" id="btn-delete-modal-call" 
                    data-id="${user.id}">Delete</button></td>
                </tr>
        `
        })
        allUsersTable.innerHTML = temp;
    }
}

function getAllUsers () {
    fetch(requestURL, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(res => res.json())
        .then(data => {
            fillUsers(data);
        })
}

getAllUsers();

const addUserForm = document.querySelector(".add-user-form");

const FormFirstName = document.getElementById("add-user-form-firstName");
const FormLastName = document.getElementById("add-user-form-lastName");
const FormAge = document.getElementById("add-user-form-age");
const FormEmail = document.getElementById("add-user-form-email");
const FormPassword = document.getElementById("add-user-form-password");
const FormRoles = document.getElementById("add-user-form-roles");
const addButtonSubmit = document.getElementById("add-btn-submit");

function getRolesFromAddUserForm() {
    let roles = Array.from(FormRoles.selectedOptions)
        .map(option => option.value);
    let rolesToAdd = [];
    if (roles.includes("1")) {
        let role1 = {
            id: 1,
            name: "ADMIN"
        }
        rolesToAdd.push(role1);
    }
    if (roles.includes("2")) {
        let role2 = {
            id: 2,
            name: "USER"
        }
        rolesToAdd.push(role2);
    }
    return rolesToAdd;
}

addUserForm.addEventListener("submit", (e) => {
    e.preventDefault();
    fetch(requestURL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            firstName: FormFirstName.value,
            lastName: FormLastName.value,
            age: FormAge.value,
            email: FormEmail.value,
            password: FormPassword.value,
            roles: getRolesFromAddUserForm()
        })
    })
        .then(() => {
            usersTableNavLink.click();
            location.reload();
        });
})

const modalEditExitBtn = document.getElementById("exit_btn-modal-edit");
const modalEditCloseBtn = document.getElementById("close_btn-modal-edit");
const modalEditSubmitBtn = document.getElementById("submit_btn-modal-edit");
const editUsersRoles = document.getElementById("edit-rolesSelect");
const editRoleAdminOption = document.getElementById("edit-role_admin");
const editRoleUserOption = document.getElementById("edit-role_user");

const deleteRoleAdminOption = document.getElementById("delete-role_admin");
const deleteRoleUserOption = document.getElementById("delete-role_user");
const modalDeleteSubmitBtn = document.getElementById("submit_btn-modal-delete");
const modalDeleteExitBtn = document.getElementById("exit_btn-modal-delete");
const modalDeleteCloseBtn = document.getElementById("close_btn-modal-delete");

function getRolesFromEditUserForm() {
    let roles = Array.from(editUsersRoles.selectedOptions)
        .map(option => option.value);
    let rolesToEdit = [];
    if (roles.includes("1")) {
        let role1 = {
            id: 1,
            name: "ADMIN"
        }
        rolesToEdit.push(role1);
    }
    if (roles.includes("2")) {
        let role2 = {
            id: 2,
            name: "USER"
        }
        rolesToEdit.push(role2);
    }
    return rolesToEdit;
}

allUsersTable.addEventListener("click", e => {
    e.preventDefault();
    let delButtonIsPressed = e.target.id === 'btn-delete-modal-call';
    let editButtonIsPressed = e.target.id === 'btn-edit-modal-call';

    const deleteUsersId = document.getElementById("delete-id")
    const deleteUsersFirstName = document.getElementById("delete-firstName")
    const deleteUsersLastName = document.getElementById("delete-lastName")
    const deleteUsersAge = document.getElementById("delete-age")
    const deleteUsersEmail = document.getElementById("delete-email")
    const deleteUsersPassword = document.getElementById("delete-password")

    if (delButtonIsPressed) {
        let currentUserId = e.target.dataset.id;
        fetch(requestURL + "/" + currentUserId, {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json;charset=UTF-8'
            }
        })
            .then(res => res.json())
            .then(user => {
                deleteUsersId.value = user.id;
                deleteUsersFirstName.value = user.firstName;
                deleteUsersLastName.value = user.lastName;
                deleteUsersAge.value = user.age;
                deleteUsersEmail.value = user.email;
                deleteUsersPassword.value = user.password;

                let deleteRoles = user.roles.map(role => role.name)
                deleteRoles.forEach(
                    role => {
                        if (role === "ROLE_ADMIN") {
                            deleteRoleAdminOption.setAttribute('selected', "selected");

                        } else if (role === "ROLE_USER") {
                            deleteRoleUserOption.setAttribute('selected', "selected");
                        }
                    })
            })
        $('#modal-delete').modal('show');

        modalDeleteSubmitBtn.addEventListener("click", e => {
            e.preventDefault();
            fetch(`${requestURL}/${currentUserId}`, {
                method: 'DELETE',
            })
                .then(res => res.json());
            modalDeleteExitBtn.click();
            getAllUsers();
            location.reload();
        })
    }

    const editUsersId = document.getElementById("edit-id");
    const editUsersFirstName = document.getElementById("edit-firstName");
    const editUsersLastName = document.getElementById("edit-lastName");
    const editUsersAge = document.getElementById("edit-age");
    const editUsersEmail = document.getElementById("edit-email");
    const editUsersPassword = document.getElementById("edit-password");

    if (editButtonIsPressed) {
        let currentUserId = e.target.dataset.id;
        fetch(requestURL + "/" + currentUserId, {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json;charset=UTF-8'
            }
        })
            .then(res => res.json())
            .then(user => {

                editUsersId.value = user.id;
                editUsersFirstName.value = user.firstName;
                editUsersLastName.value = user.lastName;
                editUsersAge.value = user.age;
                editUsersEmail.value = user.email;
                editUsersPassword.value = user.password;

                let editRoles = user.roles.map(role => role.name)
                editRoles.forEach(
                    role => {
                        if (role === "ROLE_ADMIN") {
                            editRoleAdminOption.setAttribute('selected', "selected");

                        } else if (role === "ROLE_USER") {
                            editRoleUserOption.setAttribute('selected', "selected");
                        }
                    })
            })
        $('#modal-edit').modal('show');

        modalEditSubmitBtn.addEventListener("click", e => {
            e.preventDefault();
            let user = {
                id: editUsersId.value,
                firstName: editUsersFirstName.value,
                lastName: editUsersLastName.value,
                age: editUsersAge.value,
                email: editUsersEmail.value,
                password: editUsersPassword.value,
                roles: getRolesFromEditUserForm()
            }
            fetch(`${requestURL}/${currentUserId}`, {
                method: 'PUT',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json;charset=UTF-8'
                },
                body: JSON.stringify(user)
            })
                .then(res => console.log(res));
            modalEditExitBtn.click();
            getAllUsers();
            location.reload();
        })
    }
})

let removeSelectedRolesFromEditDoc = () => {
    if (editRoleAdminOption.hasAttribute('selected')) {
        editRoleAdminOption.removeAttribute('selected')
    }
    if (editRoleUserOption.hasAttribute('selected')) {
        editRoleUserOption.removeAttribute('selected')
    }
}
modalEditExitBtn.addEventListener("click", e => {
    e.preventDefault();
    removeSelectedRolesFromEditDoc();
})
modalEditCloseBtn.addEventListener("click", e => {
    e.preventDefault();
    removeSelectedRolesFromEditDoc();
})

let removeSelectedRolesFromDeleteDoc = () => {
    if (deleteRoleAdminOption.hasAttribute('selected')) {
        deleteRoleAdminOption.removeAttribute('selected')
    }
    if (deleteRoleUserOption.hasAttribute('selected')) {
        deleteRoleUserOption.removeAttribute('selected')
    }
}
modalDeleteExitBtn.addEventListener("click", e => {
    e.preventDefault();
    removeSelectedRolesFromDeleteDoc();
})
modalDeleteCloseBtn.addEventListener("click", e => {
    e.preventDefault();
    removeSelectedRolesFromDeleteDoc();
})

const userPanelData = document.getElementById("user_panel-data");
const authorisedUserData = document.getElementById("authorised_user-data");

let currentUser = () => {
    fetch ("http://localhost:8080/api/user", {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(res => res.json())
        .then(user => {
            if (user != null) {
                userPanelData.innerHTML = `
                    <tr>
                        <td> ${user.id} </td>
                        <td> ${user.firstName} </td>
                        <td> ${user.lastName} </td>
                        <td> ${user.age} </td>
                        <td> ${user.email} </td>
                        <td> ${user.roles.map(role => " " + role.name.substring(5))} </td>
                    </tr>
                `
                authorisedUserData.innerHTML = `
                    <p class="d-inline font-weight-bold"><b>${user.email}</b> with roles ${user.roles.map(role => " " + role.name.substring(5))}</p>`
            }
        })
}
currentUser();