# Yukool Frontend

Interface web moderne pour l’exploration et la recherche de produits alimentaires (Nutri-Score, ingrédients, additifs, allergènes, etc.).

## 🚀 Fonctionnalités principales
- Recherche de produits par marque, catégorie, ou combinaison des deux
- Affichage dynamique des résultats via API (fetch)
- Filtres interactifs (input, slider)
- Affichage détaillé : Nutri-Score, nutriments, ingrédients, additifs, allergènes
- UI responsive, moderne et accessible

## 🛠️ Prérequis
- Node.js >= 21.x
- npm >= 8.x (ou yarn)

## 📦 Installation
```bash
cd Front
npm install
```

## 🏃‍♂️ Lancer le projet en développement
```bash
npm run dev
```
Le site sera accessible sur [http://localhost:5173](http://localhost:5173) par défaut.

## 🌐 Proxy API (Vite)
Le front est configuré pour proxyfier les appels API vers un backend sur `http://localhost:8080`.

**Exemple d’appel API côté front :**
```js
fetch('/products/top-by-brand?brand=Yoplait&limit=10')
```
> Vite redirige automatiquement vers `http://localhost:8080/products/top-by-brand?...`

Si besoin, modifiez le proxy dans `vite.config.js`.

## 📁 Structure des pages principales
- `src/pages/TopByBrand.jsx` : Top produits par marque
- `src/pages/TopByCategory.jsx` : Top produits par catégorie
- `src/pages/TopByBrandCategory.jsx` : Top produits par marque + catégorie
- `src/pages/TopIngredients.jsx` : Ingrédients les plus courants
- `src/pages/TopAllergens.jsx` : Allergènes les plus courants
- `src/pages/TopAdditives.jsx` : Additifs les plus courants

## ✏️ Personnalisation
- Les endpoints API sont configurés pour fonctionner avec le backend Yukool (Spring Boot)
- Pour changer l’URL de l’API, modifiez le proxy dans `vite.config.js`
- Les composants utilisent Material UI (MUI) pour un style moderne et personnalisable

## 🧑‍💻 Commandes utiles
- `npm run dev` : Lancer le serveur de dev
- `npm run build` : Build de production
- `npm run lint` : Linter le code

## 🤝 Contact
Pour toute question ou contribution : [github.com/devduo](https://github.com/devduo)
