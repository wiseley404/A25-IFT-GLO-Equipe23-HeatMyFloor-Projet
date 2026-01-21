# 🔥 HeatMyFloor - Heated Floor Design Application

A Java-based desktop application for modeling and designing heated floor systems with automated wire path generation.

**University Team Project** - Developed collaboratively for IFT-2007/GLO-2004 courses at Université Laval (Fall 2025).

## 📹 Demo

![Demo](Demo%20Heatmyfloor.gif)

*Full demo video: [https://1drv.ms/v/c/8c0ddc1208bfc810/IQA16OWgem3LRYmcMS83wAz0ASPZcuLVOPl9OH0SHJgrNiQ]*

## 📸 Screenshots

![Screenshot 1]
*Room design with furniture placement*
<img width="1920" height="1032" alt="image" src="https://github.com/user-attachments/assets/202b4cbb-64d4-49f9-83a5-9a6be08e023b" />


![Screenshot 2]
*Automated heating wire path generation*
<img width="1920" height="1032" alt="image" src="https://github.com/user-attachments/assets/f8293d21-de96-48f9-9280-5b008f259b9f" />


![Screenshot 3]
*Irregular room shapes with constraints*
<img width="1920" height="1032" alt="image" src="https://github.com/user-attachments/assets/1a12c3b3-b4e1-4370-970d-65808914da99" />


## ✨ Key Features

### Room Design
- **Rectangular rooms** with customizable dimensions
- **Irregular polygon rooms** with interactive point placement
- **Real-time visual rendering** of floor plans
- **Multi-project support** via tabbed interface

### Furniture & Objects
- **Drag-and-drop furniture placement** (with/without drains)
- **Wall-mounted objects** support
- **Restricted zones** configuration
- **Collision detection** and boundary validation
- **Automatic repositioning** on room resize

### Heating System
- **Automated wire path generation** using constraint-based algorithms
- **Obstacle avoidance** for furniture, walls, and restricted zones
- **Heating membrane generation**
- **Thermostat placement**
- **Customizable wire spacing** and patterns

### Advanced Features
- **Undo/Redo functionality** (Memento design pattern)
- **Save/Load projects** (file persistence)
- **Error handling** with visual feedback
- **Configuration panel** for precise measurements
- **Export floor designs**

## 🛠️ Tech Stack

- **Java** (Desktop application)
- **Swing** (UI framework)
- **Custom graph algorithms** for path generation
- **Hexagonal architecture** (Domain-driven design)

## 📁 Project Structure
```
src/main/java/com/heatmyfloor/
├── domain/                    # Business logic
│   ├── graphe/               # Graph & path generation algorithms
│   │   ├── Chemin.java
│   │   ├── Fil.java
│   │   ├── GenerateurChemin.java
│   │   ├── Graphe.java
│   │   └── Intersection.java
│   ├── items/                # Furniture, drains, zones
│   │   ├── MeubleAvecDrain.java
│   │   ├── MeubleSansDrain.java
│   │   ├── Thermostat.java
│   │   ├── Zone.java
│   │   └── ElementChauffant.java
│   ├── piece/                # Room models & controllers
│   │   ├── PieceRectangulaire.java
│   │   ├── PieceIrreguliere.java
│   │   ├── Mur.java
│   │   ├── Controller.java
│   │   └── PieceHistorique.java
│   ├── ports/                # Interfaces (hexagonal arch)
│   └── utilities/            # Point, Rect2D, mappers
├── gui/                      # User interface
│   ├── MainWindow.java
│   ├── Canvas.java
│   ├── BarreOutils.java
│   ├── Proprietes.java
│   ├── PositionPanel.java
│   ├── drawer/               # Rendering logic
│   └── FormeIrregulierPanel.java
├── infrastructure/           # File I/O
│   └── file/
└── HeatMyFloor.java          # Entry point
```

## 🚀 Installation & Usage

### Prerequisites
- Java JDK 11 or higher
- Maven (optional, for building from source)

### Download & Run

**Option 1: Download JAR**

Download the latest release: [📦 HeatMyFloor.jar](releases/HeatMyFloor.jar)
```bash
java -jar HeatMyFloor.jar
```

**Option 2: Build from Source**
```bash
git clone https://github.com/wiseley404/A25-IFT-GLO-Equipe23-HeatMyFloor-Projet.git
cd A25-IFT-GLO-Equipe23-HeatMyFloor-Projet
mvn clean package
java -jar target/HeatMyFloor.jar
```

## 📖 User Guide

### Creating a Room

1. **Rectangular Room**: Click "Rectangle" in toolbar → Adjust dimensions in configuration panel
2. **Irregular Room**: Click "Irregular" → Place points on canvas → Double-click to close polygon

### Adding Furniture

1. Select furniture type from toolbar (with/without drain)
2. Furniture appears at room center with default dimensions
3. Adjust position and size via configuration panel
4. Use arrow keys or drag to move

### Generating Heating Wire Path

1. Configure restricted zones and furniture placement
2. Click "Generate Wire Path"
3. Algorithm automatically calculates optimal path avoiding obstacles
4. Adjust wire spacing and pattern as needed

### Keyboard Shortcuts

- `Backspace/Delete`: Remove selected furniture
- `Ctrl+Z`: Undo
- `Ctrl+Y`: Redo
- `Ctrl+S`: Save project
- `Ctrl+N`: New project

## 🏗️ Architecture

This project follows **Hexagonal Architecture** (Ports & Adapters):

- **Domain Layer**: Core business logic (path generation, constraints, geometry)
- **GUI Layer**: Swing-based user interface
- **Infrastructure Layer**: File persistence

**Design Patterns Used:**
- **Memento** (Undo/Redo)
- **Larman Controller** (Controller, View separation)
- **Strategy** (Path generation algorithms)

## 👥 Contributors

This project was developed by:

- **[Petiton Wiseley Paul-Enzer]**
- **[Wily Tatow]**
- **[Ouedraogo Aliya Imann]**
- **[Kémila Bakary]**
- **[Dongmeza Murielle Christelle]**

## 📝 License

© 2025 Équipe 23. All rights reserved.

This project was developed as part of IFT-2007/GLO-2004 coursework at Université Laval.

---

**Note**: This application was designed for educational purposes as part of software engineering curriculum.
