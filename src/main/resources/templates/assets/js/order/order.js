document.addEventListener('DOMContentLoaded', function () {
    const radioInputs = document.querySelectorAll('input[type="radio"][name="wrapRadio"]');
    const wrapItem = document.querySelectorAll('input[type=radio][name="wrap-item"]')
    const point = document.querySelector('input[id="user-point"][type="text"]')
    const total = document.querySelector('span[id="totalCost"]')
    const totalCost = total.textContent
    radioInputs.forEach(function (input) {
        input.addEventListener('click', function () {
            const chkValue = document.getElementById('wrap-used').checked;
            const wrapViewDiv = document.getElementById('wrap-view');
            if (chkValue) {
                wrapViewDiv.style.display = 'block';
            } else {
                wrapViewDiv.style.display = 'none';
            }
        });
    });
    wrapItem.forEach(function (input) {
        input.addEventListener('click', function () {
            // 클릭 시 포장지의 가격을 total에 합쳐지는 역할
        })
    })

    point.addEventListener('keyup', function (event) {
        event.preventDefault()
        if (event.keyCode === 13) {
            const inputValue = parseInt(point.value); // 입력된 값 가져오기
            const maxValue = parseInt(point.getAttribute('max')); // max 값 가져오기
            if (inputValue > maxValue) {
                alert('최대값을 초과하였습니다.'); // 알림 표시
            } else if (inputValue < 0) {
                alert("0원 미만을 입력하셨습니다.")
            }
            updateTotalCost(inputValue)
        }
    })

    function updateTotalCost(num) {
        total.textContent = parseInt(totalCost) - num;
    }


});

function address() {
    const width = 800
    const height = 600
    const left = Math.ceil((window.screen.width - width) / 2);
    const top = Math.ceil((window.screen.height - height) / 2)
    window.open("/address", "_blank", "toolbar=yes,scrollbars=yes,top=" + top + ",left=" + left + ",width=" + width + ",height=" + height)
}

function clickCoupon() {
    var couponCode = document.getElementById('couponCode').value;

    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/", true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState == XMLHttpRequest.DONE) {
            if (xhr.status == 200) {

            } else {
            }
        }
    };
    xhr.send();
}

function wrap() {
    const width = 800
    const height = 600
    const left = Math.ceil((window.screen.width - width) / 2);
    const top = Math.ceil((window.screen.height - height) / 2)
    window.open("/checkout/wraps", "_blank", "toolbar=yes,scrollbars=yes,top=" + top + ",left=" + left + ",width=" + width + ",height=" + height)
}







