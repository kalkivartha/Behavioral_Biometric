🔐 Java Keystroke Dynamics Authentication
A lightweight desktop application that uses keystroke biometrics to authenticate users based on their unique typing rhythm. Instead of relying solely on what you type (password), this system analyzes how you type it — making security more personalized and harder to spoof.

⚙️ Features
🧠 Behavioral Biometrics – Captures dwell (key hold) and flight (between keys) times

💬 Swing-Based GUI – Simple interface with Register & Login options

📝 Local Storage – Saves your typing pattern in a .txt file

📐 Pattern Matching – Uses Euclidean distance to compare typing samples

🕹️ Offline & Lightweight – No external libraries or internet required

🚀 Getting Started
✅ Requirements
Java JDK 8 or higher

Any IDE (IntelliJ, Eclipse) or terminal compiler

▶️ Run the Application
Compile & Run:

bash
Copy
Edit
javac KeystrokeAuthSystem.java
java KeystrokeAuthSystem
Register:

Click "Register" and type your password

Typing pattern is saved automatically

Login:

Click "Login" and type the same password

System compares your current typing with the stored pattern

🧪 How It Works
During Registration:

Captures the dwell and flight times for each keystroke

Stores them as your typing signature

During Login:

Captures a new sample and compares it with the stored pattern

If the Euclidean distance is within a threshold (default 75.0), login is successful

🛠️ Customization
Adjust the sensitivity by modifying the threshold value in the code

Extend for multi-user support or integrate with ML models (like SVM) for better accuracy

👨‍💻 Ideal For
Students learning behavioral biometrics

Mini-projects or security demos

Offline authentication experiments
