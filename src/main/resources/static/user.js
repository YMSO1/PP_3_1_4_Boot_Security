const userPanelData = document.getElementById("user_panel-data");
const authorisedUserData = document.getElementById("authorised_user-data");

let currentUser = () => {
    fetch("http://localhost:8080/api/user", {
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
                        </tr>`
                authorisedUserData.innerHTML = `
                        <p class="d-inline font-weight-bold h5"><b>${user.email}</b> with roles
                        ${user.roles.map(role => " " + role.name.substring(5))}</p>`
            }
        })
}
currentUser();