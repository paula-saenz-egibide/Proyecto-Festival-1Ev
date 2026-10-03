const botones = document.querySelectorAll(".nav-item");
const vistas = document.querySelectorAll(".view");
const titulo = document.getElementById("page-title");

const nombres = {
    artistas: "Artistas",
    escenarios: "Escenarios",
    actuaciones: "Actuaciones",
    espectadores: "Espectadores",
    entradas: "Entradas"
};

const singulares = {
    artistas: "artista",
    escenarios: "escenario",
    actuaciones: "actuacion",
    espectadores: "espectador",
    entradas: "entrada"
};

let artistasCargados = [];

async function leerRespuesta(respuesta) {
    const texto = await respuesta.text();
    let resultado;

    try {
        resultado = JSON.parse(texto);
    } catch (error) {
        resultado = texto;
    }

    if (!respuesta.ok) {
        const mensaje = resultado && typeof resultado === "object"
            ? resultado.mensaje || resultado.message
            : resultado;

        throw new Error(mensaje || `Error HTTP ${respuesta.status}`);
    }

    return resultado;
}

function mostrarError(error, mensajePredeterminado) {
    return error instanceof TypeError
        ? mensajePredeterminado
        : error.message || mensajePredeterminado;
}

botones.forEach(boton => {
    boton.addEventListener("click", () => {
        const vista = boton.dataset.view;

        botones.forEach(b => b.classList.remove("active"));
        boton.classList.add("active");

        vistas.forEach(v => v.classList.remove("active"));

        document.getElementById(vista).classList.add("active");

        titulo.textContent = boton.querySelector("span").textContent;

        if (vista !== "inicio" && vista !== "archivos") {
            cargarDatos(vista);
        }
    });
});


async function cargarDatos(tipo) {
    const contenedor = document.getElementById("lista-" + tipo);

    if (!contenedor) return;

    contenedor.innerHTML = "Cargando...";

    try {
        const respuesta = await fetch("/api/" + tipo);
        let datos = await leerRespuesta(respuesta);

        if (tipo === "artistas") {
            artistasCargados = datos;
            actualizarFiltrosArtistas(datos);
            datos = filtrarListaArtistas(datos);
        }

        mostrarDatos(tipo, datos);

        actualizarEstadisticas();

    } catch (error) {
        contenedor.textContent = mostrarError(
            error,
            "No se ha podido conectar con el servidor."
        );

        console.error(error);
    }
}


function mostrarDatos(tipo, datos) {
    const contenedor = document.getElementById("lista-" + tipo);

    if (!Array.isArray(datos)) {
        contenedor.innerHTML = "No se han encontrado datos.";
        return;
    }

    if (datos.length === 0) {
        contenedor.innerHTML = "No hay datos registrados.";
        return;
    }

    const columnas = Object.keys(datos[0]);

    let html = `
        <table class="data-table">
            <thead>
                <tr>
    `;

    columnas.forEach(columna => {
        html += `<th>${columna}</th>`;
    });

    html += "<th>Acciones</th></tr></thead><tbody>";


    datos.forEach(objeto => {
        html += "<tr>";

        columnas.forEach(columna => {
            let valor = objeto[columna];

            if (valor === null || valor === undefined) {
                valor = "";
            }

            html += `<td>${valor}</td>`;
        });

        html += `
            <td>
                <button
                    class="action-button"
                    onclick="editar(${objeto.id}, '${tipo}')">
                    Editar
                </button>

                <button
                    class="action-button delete-button"
                    onclick="eliminar(${objeto.id}, '${tipo}')">
                    Eliminar
                </button>
            </td>
        `;

        html += "</tr>";
    });

    html += "</tbody></table>";

    contenedor.innerHTML = html;
}


async function buscarPorId(tipo) {
    const input = document.getElementById(
        "buscar-" + singulares[tipo]
    );

    const id = input.value;

    if (!id) {
        cargarDatos(tipo);
        return;
    }

    try {
        const respuesta = await fetch(
            `/api/${tipo}/${id}`
        );
        const objeto = await leerRespuesta(respuesta);

        const contenedor =
            document.getElementById("lista-" + tipo);

        if (!objeto) {
            contenedor.innerHTML =
                "No se ha encontrado el registro.";

            return;
        }

        mostrarDatos(tipo, [objeto]);

    } catch (error) {
        console.error(error);

        document.getElementById("lista-" + tipo).textContent =
            mostrarError(error, "No se ha podido conectar con el servidor.");
    }
}

function actualizarFiltrosArtistas(artistas) {
    const filtros = [
        {
            id: "filtro-genero-artista",
            etiqueta: "Todos los géneros",
            campo: "genero"
        },
        {
            id: "filtro-pais-artista",
            etiqueta: "Todos los países",
            campo: "pais"
        }
    ];

    filtros.forEach(({ id, etiqueta, campo }) => {
        const select = document.getElementById(id);
        const valorAnterior = select.value;
        const valores = [...new Set(
            artistas
                .map(artista => artista[campo])
                .filter(valor => valor !== null && valor !== undefined && valor !== "")
        )].sort((a, b) => String(a).localeCompare(String(b), "es"));

        select.replaceChildren(new Option(etiqueta, ""));

        valores.forEach(valor => {
            select.add(new Option(valor, valor));
        });

        if (valores.includes(valorAnterior)) {
            select.value = valorAnterior;
        }
    });
}

function filtrarListaArtistas(artistas) {
    const genero = document.getElementById("filtro-genero-artista").value;
    const pais = document.getElementById("filtro-pais-artista").value;

    return artistas.filter(artista =>
        (!genero || artista.genero === genero) &&
        (!pais || artista.pais === pais)
    );
}

function filtrarArtistas() {
    mostrarDatos("artistas", filtrarListaArtistas(artistasCargados));
}


async function eliminar(id, tipo) {
    if (!confirm("¿Quieres eliminar este registro?")) {
        return;
    }

    try {
        const respuesta = await fetch(
            `/api/${tipo}/${id}`,
            {
                method: "DELETE"
            }
        );

        const resultado = await leerRespuesta(respuesta);

        if (resultado.exito === true) {
            cargarDatos(tipo);
            actualizarEstadisticas();
        } else {
            alert(resultado.mensaje || "No se ha podido eliminar.");
        }

    } catch (error) {
        alert(mostrarError(error, "Error al conectar con el servidor."));
        console.error(error);
    }
}


async function mostrarFormulario(tipo, id = null) {
    const contenedor =
        document.getElementById("form-" + tipo);

    contenedor.classList.remove("hidden");

    contenedor.innerHTML = "Cargando formulario...";

    try {
        const respuesta =
            await fetch(`/api/esquema/${tipo}`);

        const campos = await leerRespuesta(respuesta);

        let datos = {};

        if (id !== null) {
            const respuestaDatos =
                await fetch(`/api/${tipo}/${id}`);

            datos = await leerRespuesta(respuestaDatos);
        }

        let html = `
            <h3>
                ${id === null ? "Nuevo" : "Editar"}
                ${nombres[tipo]}
            </h3>

            <div class="form-grid">
        `;

        Object.entries(campos).forEach(([nombre, tipoDato]) => {

            let valor = datos[nombre] ?? "";

            let tipoInput = "text";

            if (tipoDato === "int" ||
                tipoDato === "Integer" ||
                tipoDato === "long" ||
                tipoDato === "Long") {
                tipoInput = "number";
            }

            if (tipoDato === "LocalDate") {
                tipoInput = "date";
            }

            if (nombre === "email") {
                tipoInput = "email";
            }

            if (tipoDato === "boolean" || tipoDato === "Boolean") {
                tipoInput = "checkbox";
            }

            const valorAtributo = tipoInput === "checkbox"
                ? (valor ? "checked" : "")
                : `value="${valor}"`;

            html += `
                <div class="form-group ${tipoInput === "checkbox" ? "checkbox-group" : ""}">
                    <label>${nombre}</label>

                    <input
                        id="campo-${nombre}"
                        data-campo="${nombre}"
                        data-tipo="${tipoDato}"
                        type="${tipoInput}"
                        ${valorAtributo}
                        ${nombre === "id" && id !== null ? "readonly" : ""}
                    >
                </div>
            `;
        });

        html += `
            </div>

            <div class="form-buttons">

                <button
                    class="primary-button"
                    onclick="guardarFormulario('${tipo}', ${id === null ? "null" : id})">
                    Guardar
                </button>

                <button
                    class="action-button"
                    onclick="cerrarFormulario('${tipo}')">
                    Cancelar
                </button>

            </div>
        `;

        contenedor.innerHTML = html;

    } catch (error) {
        console.error(error);

        contenedor.textContent =
            mostrarError(error, "No se ha podido cargar el formulario.");
    }
}


async function guardarFormulario(tipo, id) {
    const inputs =
        document.querySelectorAll(
            `#form-${tipo} input`
        );

    const objeto = {};

    inputs.forEach(input => {
        const campo = input.dataset.campo;
        const tipoDato = input.dataset.tipo;

        let valor = tipoDato === "boolean" || tipoDato === "Boolean"
            ? input.checked
            : input.value;

        if (tipoDato === "int" ||
            tipoDato === "Integer") {
            valor = Number(valor);
        }

        if (tipoDato === "long" ||
            tipoDato === "Long") {
            valor = Number(valor);
        }

        objeto[campo] = valor;
    });

    const inputEmail = [...inputs].find(input => input.dataset.campo === "email");
    if (inputEmail && !/^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/.test(inputEmail.value)) {
        alert("Error: introduce un email válido, por ejemplo usuario@gmail.com.");
        inputEmail.focus();
        return;
    }

    try {
        const metodo =
            id === null ? "POST" : "PUT";

        const respuesta =
            await fetch(`/api/${tipo}`, {
                method: metodo,
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(objeto)
            });

        const resultado = await leerRespuesta(respuesta);

        if (resultado.exito === true) {

            alert(
                id === null
                    ? "Registro guardado correctamente."
                    : "Registro modificado correctamente."
            );

            cerrarFormulario(tipo);
            cargarDatos(tipo);
            actualizarEstadisticas();

        } else {
            alert(
                resultado.mensaje ||
                "No se ha podido guardar o modificar el registro."
            );
        }

    } catch (error) {
        alert(mostrarError(error, "Error al conectar con el servidor."));
        console.error(error);
    }
}


function editar(id, tipo) {
    mostrarFormulario(tipo, id);
}


function cerrarFormulario(tipo) {
    const contenedor =
        document.getElementById("form-" + tipo);

    contenedor.classList.add("hidden");
    contenedor.innerHTML = "";
}


async function generarArchivo(tipo, formato) {
    try {
        const respuesta =
            await fetch(
                `/api/archivos/${tipo}/${formato}/generar`
            );

        const resultado = await leerRespuesta(respuesta);

        if (resultado === true) {
            alert(
                `${tipo}.${formato} generado correctamente.`
            );
        }

    } catch (error) {
        alert(mostrarError(error, "No se ha podido generar el archivo."));

        console.error(error);
    }
}


function descargarArchivo(tipo, formato) {
    window.location.href =
        `/api/archivos/${tipo}/${formato}/descargar`;
}


async function actualizarEstadisticas() {
    try {
        const respuestaArtistas = await fetch("/api/artistas");
        const artistas = await leerRespuesta(respuestaArtistas);

        const respuestaEscenarios = await fetch("/api/escenarios");
        const escenarios = await leerRespuesta(respuestaEscenarios);

        const respuestaEntradas = await fetch("/api/entradas");
        const entradas = await leerRespuesta(respuestaEntradas);

        document.getElementById(
            "total-artistas"
        ).textContent = artistas.length;

        document.getElementById(
            "total-escenarios"
        ).textContent = escenarios.length;

        document.getElementById(
            "total-entradas"
        ).textContent = entradas.length;

    } catch (error) {
        console.error(error);
    }
}


actualizarEstadisticas();