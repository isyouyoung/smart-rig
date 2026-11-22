// 페이지가 다 그려지면 실행
$(function () {
    loadPcList();

    // + PC 등록 버튼: 입력 칸 펼치기/접기
    $("#btnShowPcForm").on("click", function () {
        $("#pcForm").toggle();
        $("#pcName").focus();
    });

    // 취소 버튼
    $("#btnCancelPc").on("click", resetPcForm);

    // 저장 버튼
    $("#btnSavePc").on("click", savePc);
});

// 입력 칸 비우고 접기
function resetPcForm() {
    $("#pcName").val("");
    $("#pcSpecs").val("");
    $("#pcPrice").val("");
    $("#pcStatus").val("SALE");
    $("#pcForm").hide();
}

// PC 저장
function savePc() {
    const name = $("#pcName").val().trim();
    const specs = $("#pcSpecs").val().trim();
    const priceText = $("#pcPrice").val();

    if (name === "") {
        alert("이름을 입력해 주세요.");
        $("#pcName").focus();
        return;
    }

    if (specs === "") {
        alert("스펙을 입력해 주세요.");
        $("#pcSpecs").focus();
        return;
    }

    if (priceText === "" || !Number.isInteger(Number(priceText)) || Number(priceText) < 0) {
        alert("가격을 0원 이상의 숫자로 입력해 주세요.");
        $("#pcPrice").focus();
        return;
    }

    // 저장하는 동안 버튼을 잠가서 두 번 눌러 중복 등록되는 걸 막는다.
    $("#btnSavePc").prop("disabled", true);

    $.ajax({
        url: "/api/v1/pcs",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({
            name: name,
            specs: specs,
            price: Number(priceText),
            status: $("#pcStatus").val()
        }),
        success: function () {
            resetPcForm();
            loadPcList();   // 표 새로고침 (새 PC는 맨 아래에 붙는다)
        },
        error: function (xhr) {
            if (xhr.status === 401 || xhr.status === 403) {
                alert("로그인이 필요합니다. 로그인해 주세요.");
            } else if (xhr.responseJSON && xhr.responseJSON.message) {
                alert(xhr.responseJSON.message);
            } else {
                alert("저장에 실패했습니다. (" + xhr.status + ")");
            }
        },
        complete: function () {
            $("#btnSavePc").prop("disabled", false);
        }
    });
}

// API에서 PC 목록 가져오기
function loadPcList() {
    $.ajax({
        url: "/api/v1/pcs",
        type: "GET",
        success: function (pcList) {
            renderPcList(pcList);
        },
        error: function (xhr) {
            alert("PC 목록을 불러오지 못했습니다. (" + xhr.status + ")");
        }
    });
}

// 받아온 목록으로 표 채우기
function renderPcList(pcList) {
    const $tbody = $("#pcList");
    $tbody.empty();   // 기존 줄 비우기

    if (pcList.length === 0) {
        $tbody.append('<tr><td colspan="4">등록된 PC가 없습니다.</td></tr>');
        return;
    }

    pcList.forEach(function (pc) {
        const $row = $("<tr>");

        $row.append($("<td>").text(pc.name));
        $row.append($("<td>").text(pc.specs));
        $row.append($("<td>").text(pc.price.toLocaleString("ko-KR") + "원"));
        $row.append($("<td>").text(pc.status === "SALE" ? "판매중" : "판매완료"));

        if (pc.status === "SOLD_OUT") {
            $row.addClass("sold-out");   // 품절 줄은 나중에 CSS로 흐리게
        }

        $tbody.append($row);
    });
}