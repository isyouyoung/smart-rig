$(document).ready(function () {

    // Intel 선택
    $(".manufacturer-btn").on("click", function () {

        let manufacturer = $(this).data("manufacturer");

        console.log("선택 제조사 : " + manufacturer);

        if (manufacturer === "Intel") {

            $.ajax({
                url: "/model/v1/getCpuListWithStock",
                type: "GET",
                dataType: "JSON"
            }).then(
                function (list) {

                    console.log("Intel CPU 목록 : ", list);

                    let generationSet = new Set();

                    list.forEach(function (cpu) {

                        if (cpu.quantity > 0) {

                            generationSet.add(cpu.generation);

                        }

                    });

                    console.log("재고 있는 Intel 세대 : ", generationSet);

                    let html = "<h2>세대 선택</h2>";

                    generationSet.forEach(function (generation) {

                        html +=
                            "<button class='generation-btn' data-generation='" + generation + "'>"
                            + generation + "세대"
                            + "</button>";

                    });

                    $("#generationMenu").html(html);

                },
                function (xhr) {

                    console.log("Intel CPU 조회 실패 : ", xhr);

                }
            );

        }

    });

});