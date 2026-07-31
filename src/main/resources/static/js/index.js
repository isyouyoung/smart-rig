$(document).ready(function () {

    // 재고 조회 버튼
    $("#btnSearchStock").on("click", function () {

        console.log("조회 버튼 클릭");

        let modelId = $("#modelId").val();

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