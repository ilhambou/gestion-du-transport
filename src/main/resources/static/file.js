document.addEventListener("DOMContentLoaded", function() {
    // Récupérer les pourcentages depuis les attributs HTML
    const malePercentage = parseFloat(document.getElementById("malePercentage").getAttribute("data-percentage"));
    const femalePercentage = parseFloat(document.getElementById("femalePercentage").getAttribute("data-percentage"));

    // Créer un tableau de données pour le graphique
    const data = {
        labels: ["Hommes", "Femmes"],
        datasets: [{
            data: [malePercentage, femalePercentage],
            backgroundColor: ["blue", "pink"], // Couleurs des segments
        }],
    };

    // Configuration du graphique
    const config = {
        type: "pie",
        data: data,
    };

    // Créer le graphique
    const ctx = document.getElementById("genderChart").getContext("2d");
    new Chart(ctx, config);
});
