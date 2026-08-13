$(document).ready(function () {

    let cpuList = [];

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

                    cpuList = list;

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

    $(document).on("click", ".generation-btn", function () {

        let generation = String($(this).data("generation"));

        console.log("선택 세대 : " + generation);

        let availableCpuList = cpuList.filter(function (cpu) {

            return cpu.generation === generation
                && cpu.quantity > 0;

        });

        console.log("선택 세대 재고 CPU : ", availableCpuList);

        let seriesList = {
            "Celeron": [],
            "Pentium": [],
            "i3": [],
            "i5": [],
            "i7": [],
            "i9": []
        };

        availableCpuList.forEach(function (cpu) {

            seriesList[cpu.series].push(cpu);

        });

        console.log("Series별 CPU : ", seriesList);

        let html = "<h2>" + generation + "세대 CPU 선택</h2>";

        Object.keys(seriesList).forEach(function (series) {

            let list = seriesList[series];

            if (list.length === 0) {
                return;
            }

            html += "<h3>" + series + "</h3>";

            list.forEach(function (cpu) {

                html +=
                    "<button type='button' class='cpu-select-btn' data-model-id='" + cpu.modelId + "'>"
                    + cpu.modelName
                    + " (" + cpu.quantity + "개)"
                    + "</button>";

            });

        });

        $("#generationMenu").html(html);

    });

});
