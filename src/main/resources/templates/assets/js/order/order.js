document.addEventListener('DOMContentLoaded', function (event
) {
    const radioInputs = document.querySelectorAll('input[type="radio"][name="wrapRadio"]');
    const point = document.querySelector('input[id="user-point"][type="text"]')
    const wrapCost = document.querySelector('input[id="wrap-cost"]')
    // const input = document.querySelectorAll(".select-wrap")
    let data = 0;
    alert("확인용")

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
    wrapCost.addEventListener('change', function () {
        alert(wrapCost.textContent)
        updateTotalCost(parseInt(wrapCost.textContent))
    })

    point.addEventListener('keyup', function (event) {
        event.preventDefault()
        if (event.keyCode === 13) {
            const total = document.querySelector('span[id="totalCost"]')

            if (point.value === "") {
                point.value = "0"
            }
            const inputValue = parseInt(point.value); // 입력된 값 가져오기
            const maxValue = parseInt(point.getAttribute('max')); // max 값 가져오기
            if (inputValue > maxValue) {
                alert('최대값을 초과하였습니다.'); // 알림 표시
            } else if (inputValue < 0) {
                alert("0원 미만을 입력하셨습니다.")
            }

            total.textContent = updateTotalCost(parseInt(total.textContent) + data, inputValue);
            data = inputValue;
        }
    })
    // point.addEventListener('change', function (event) {
    //     event.preventDefault()
    //     const inputValue = parseInt(point.value); // 입력된 값 가져오기
    //     const maxValue = parseInt(point.getAttribute('max')); // max 값 가져오기
    //     if (inputValue > maxValue) {
    //         alert('최대값을 초과하였습니다.'); // 알림 표시
    //     } else if (inputValue < 0) {
    //         alert("0원 미만을 입력하셨습니다.")
    //     }
    //     updateTotalCost(inputValue)
    //
    // })


});

function address() {
    const width = 800
    const height = 600
    const left = Math.ceil((window.screen.width - width) / 2);
    const top = Math.ceil((window.screen.height - height) / 2)
    window.open("/address", "_blank", "toolbar=yes,scrollbars=yes,top=" + top + ",left=" + left + ",width=" + width + ",height=" + height)
}

function clickCoupon() {
    const bookId = document.getElementById("coupon-button").value;
    const width = 800
    const height = 600
    const left = Math.ceil((window.screen.width - width) / 2);
    const top = Math.ceil((window.screen.height - height) / 2)
    window.open("/checkout/coupon/" + bookId, "_blank", "toolbar=yes,scrollbars=yes,top=" + top + ",left=" + left + ",width=" + width + ",height=" + height)
}

function wrap(button) {
    const id = button.parentElement.parentElement.id
    const width = 800
    const height = 600
    const left = Math.ceil((window.screen.width - width) / 2);
    const top = Math.ceil((window.screen.height - height) / 2)
    window.open("/checkout/wraps/" + id, "_blank", "toolbar=yes,scrollbars=yes,top=" + top + ",left=" + left + ",width=" + width + ",height=" + height)
}

function updateTotalCost(total, num) {
    return total - num;
}







