// 페이지가 다 그려지면 실행
$(function () {
    loadPcList();
});

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