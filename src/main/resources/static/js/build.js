$(document).ready(function () {

    // Intel 선택
    $(".manufacturer-btn").on("click", function () {

        let manufacturer = $(this).data("manufacturer");

        console.log("선택 제조사 : " + manufacturer);

        if (manufacturer === "Intel") {

            $("#generationMenu").html(
                "<h2>세대 선택</h2>"
                + "<button class='generation-btn' data-generation='2'>2세대</button>"
                + "<button class='generation-btn' data-generation='3'>3세대</button>"
                + "<button class='generation-btn' data-generation='4'>4세대</button>"
                + "<button class='generation-btn' data-generation='6'>6세대</button>"
                + "<button class='generation-btn' data-generation='7'>7세대</button>"
                + "<button class='generation-btn' data-generation='8'>8세대</button>"
                + "<button class='generation-btn' data-generation='9'>9세대</button>"
                + "<button class='generation-btn' data-generation='10'>10세대</button>"
            );

        }

    });

});