const botones = document.querySelectorAll(".nav-item");
const vistas = document.querySelectorAll(".view");
const titulo = document.getElementById("page-title");

botones.forEach(boton => {
    boton.addEventListener("click", () => {

        const vista = boton.dataset.view;

        botones.forEach(b => {
            b.classList.remove("active");
        });

        boton.classList.add("active");

        vistas.forEach(v => {
            v.classList.remove("active");
        });

        document.getElementById(vista).classList.add("active");

        titulo.textContent = boton.querySelector("span").textContent;
    });
});