let userId = getUrlParameter('userId')

function setUserId(userId) {
    localStorage.setItem('userId', userId);
    document.getElementById('userId').value = userId;
}

if (userId) {
    setUserId(userId);
} else {
    const storedUserId = localStorage.getItem('userId');
    if (storedUserId) {
        window.location.href = '/?userId=' + storedUserId;
    } else {
        document.getElementById('createUser').value = true;
    }
}

let marsApiButtons = document.querySelectorAll("button[id*='roverTypeBtn']")

marsApiButtons.forEach(button => button.addEventListener('click', function () {
    const buttonId = this.id
    const roverId = buttonId.split('roverTypeBtn')[1]
    let apiData = document.getElementById('roverType')
    apiData.value = roverId
    document.getElementById('frmRoverType').submit()
}))

function getUrlParameter(name) {
    name = name.replace(/\[/, '\\[').replace(/]/, '\\]');
    const regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
    const results = regex.exec(location.search);
    return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
}

let marsRoverType = document.getElementById('roverType').value

highlightBtnByRoverType(marsRoverType)

let marsSol = document.getElementById('marsSol').value
if (marsSol != null && marsSol !== '' && marsSol >= 0) {
    document.getElementById('marsSol').value = marsSol
}

function highlightBtnByRoverType(roverType) {
    roverType = roverType === '' ? 'Opportunity' : roverType;
    const roverTypeBtn = document.getElementById('roverTypeBtn' + roverType);
    roverTypeBtn.classList.remove('btn-secondary');
    roverTypeBtn.classList.add('btn-primary');
}
