$(document).ready(function () {

    // 재고 조회 버튼
    $("#btnSearchStock").on("click", function () {

        console.log("조회 버튼 클릭");

        let modelId = $("#modelId").val();


        // Model ID 입력 여부 확인 (빈칸 시 안내 메시지 출력)
        if (modelId === "") {

            alert("Model ID를 입력하세요.");

            $("#modelId").focus();

            return;
        }

        console.log("입력한 Model ID : " + modelId);


        $.ajax({

            url: "/stock/v1/getStockByModelId",
            type: "GET",
            data: {
                modelId: modelId
            },
            dataType: "JSON"

        }).then(function (json) {

                console.log(json);

                $("#stockModel").text(
                    "Model ID : " + json.modelId
                );

                $("#stockQuantity").text(
                    "수량 : " + json.quantity
                );

            },
            function (xhr) {

                alert(xhr.responseJSON.message);

                $("#stockModel").text("");
                $("#stockQuantity").text("");

            });

    });


    // 메인 화면 이동
    $("#btnHome").on("click", function () {

        location.href = "/html/index.html";

    });

});