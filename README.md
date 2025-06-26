# BuildCrew 🚀

A modern Android app for connecting developers, designers, and creators to collaborate on projects. Built with Kotlin, Jetpack Compose, and Firebase.

## 📱 Features

### 🔐 Authentication
- **Google Sign-In** - Seamless authentication with Google accounts
- **Firebase Auth** - Secure user management and session handling
- **Profile Setup** - Customizable user profiles with skills and bio

### 🏠 Project Management
- **Create Projects** - Start new projects with title, description, and required skills
- **Browse Feed** - Discover projects from other users
- **Project Details** - View project information and join requests
- **Join Requests** - Request to join projects and manage invitations

### 💬 Real-time Chat
- **Project Chat** - Dedicated chat rooms for each project
- **Real-time Messages** - Instant message updates using Firestore
- **Message History** - Persistent chat history across sessions

### 🎨 Modern UI/UX
- **Material Design 3** - Latest Material Design components
- **Dark/Light Theme** - Automatic theme switching
- **Responsive Design** - Optimized for different screen sizes
- **Smooth Navigation** - Intuitive navigation with back buttons

## 🛠 Tech Stack

### Frontend
- **Kotlin** - Primary programming language
- **Jetpack Compose** - Modern declarative UI toolkit
- **Material Design 3** - Latest design system
- **Navigation Compose** - Type-safe navigation

### Backend & Services
- **Firebase Authentication** - User authentication
- **Cloud Firestore** - NoSQL database for real-time data
- **Firebase Security Rules** - Secure data access

### Architecture
- **MVVM** - Model-View-ViewModel architecture
- **Repository Pattern** - Clean data layer abstraction
- **Hilt** - Dependency injection
- **Coroutines** - Asynchronous programming
- **Flow** - Reactive streams

### Libraries
- **Coil** - Image loading
- **Google Sign-In** - OAuth authentication
- **Firebase BOM** - Firebase dependency management

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or later
- Android SDK 34 or later
- Kotlin 1.9.24
- Google account for Firebase setup

### Setup Instructions

1. **Clone the repository**
   ```bash
   git clone https://github.com/aditya0l/BuildCrew.git
   cd BuildCrew
   ```

2. **Firebase Setup**
   - Go to [Firebase Console](https://console.firebase.google.com/)
   - Create a new project
   - Add Android app with package name: `com.example.buildcrew`
   - Download `google-services.json` and place it in the `app/` directory
   - Enable Authentication with Google Sign-In
   - Enable Firestore Database
   - Update Firestore security rules (see below)

3. **Google Sign-In Setup**
   - In Firebase Console, go to Authentication > Sign-in method
   - Enable Google Sign-In
   - Add your SHA-1 fingerprint to the project
   - Copy the Web Client ID to `LoginViewModel.kt`

4. **Firestore Security Rules**
   ```javascript
   rules_version = '2';
   service cloud.firestore {
     match /databases/{database}/documents {
       // Users can read/write their own user document
       match /users/{userId} {
         allow read, write: if request.auth != null && request.auth.uid == userId;
       }
       
       // Users can read all projects and create their own
       match /projects/{projectId} {
         allow read: if request.auth != null;
         allow create: if request.auth != null && request.auth.uid == resource.data.ownerId;
         allow update, delete: if request.auth != null && request.auth.uid == resource.data.ownerId;
       }
       
       // Users can read/write join requests for projects they own or created
       match /projects/{projectId}/joinRequests/{requestId} {
         allow read, write: if request.auth != null && 
           (request.auth.uid == resource.data.userId || 
            request.auth.uid == get(/databases/$(database)/documents/projects/$(projectId)).data.ownerId);
       }
       
       // Users can read/write messages in project chats
       match /projects/{projectId}/messages/{messageId} {
         allow read, write: if request.auth != null;
       }
     }
   }
   ```

5. **Build and Run**
   ```bash
   ./gradlew build
   ```
   - Open project in Android Studio
   - Sync Gradle files
   - Run on device or emulator

## 📁 Project Structure

```
app/src/main/java/com/example/buildcrew/
├── BuildCrewApp.kt                 # Application class
├── MainActivity.kt                 # Main activity with navigation
├── data/
│   ├── model/                      # Data models
│   │   ├── User.kt
│   │   ├── Project.kt
│   │   ├── JoinRequest.kt
│   │   └── Message.kt
│   ├── repository/                 # Repository interfaces
│   │   ├── AuthRepository.kt
│   │   ├── UserRepository.kt
│   │   ├── ProjectRepository.kt
│   │   └── ChatRepository.kt
│   └── source/                     # Firebase implementations
│       ├── FirebaseAuthRepository.kt
│       ├── FirebaseUserRepository.kt
│       ├── FirebaseProjectRepository.kt
│       └── FirebaseChatRepository.kt
├── di/
│   └── AppModule.kt               # Hilt dependency injection
└── ui/
    ├── nav/
    │   └── NavGraph.kt            # Navigation setup
    ├── screens/                   # UI screens
    │   ├── LoginScreen.kt
    │   ├── SignupScreen.kt
    │   ├── ProfileSetupScreen.kt
    │   ├── HomeFeedScreen.kt
    │   ├── ProjectCreateScreen.kt
    │   ├── ProjectDetailScreen.kt
    │   └── ChatScreen.kt
    ├── theme/                     # UI theme
    │   ├── Color.kt
    │   ├── Theme.kt
    │   └── Type.kt
    └── ViewModels/                # ViewModels
        ├── LoginViewModel.kt
        ├── SignupViewModel.kt
        ├── ProfileSetupViewModel.kt
        ├── HomeFeedViewModel.kt
        ├── ProjectCreateViewModel.kt
        ├── ProjectDetailViewModel.kt
        └── ChatViewModel.kt
```

## 🎯 Usage Guide

### For Users
1. **Sign In** - Use Google account to authenticate
2. **Setup Profile** - Add your skills, bio, and profile picture
3. **Browse Projects** - View available projects in the home feed
4. **Create Projects** - Start your own projects and find collaborators
5. **Join Projects** - Request to join interesting projects
6. **Chat** - Communicate with team members in project chat rooms

### For Developers
- Follow MVVM architecture patterns
- Use Hilt for dependency injection
- Implement repository pattern for data access
- Use Coroutines for asynchronous operations
- Follow Material Design 3 guidelines

## 🔧 Configuration

### Gradle Dependencies
Key dependencies are managed in `gradle/libs.versions.toml`:
- Compose BOM: 2024.02.00
- Kotlin: 1.9.24
- Hilt: 2.50
- Firebase BOM: 32.7.2

### Build Configuration
- Minimum SDK: 24
- Target SDK: 34
- Compile SDK: 34

## 🐛 Troubleshooting

### Common Issues
1. **Google Sign-In Error (ApiException 10)**
   - Ensure SHA-1 fingerprint is registered in Firebase
   - Verify `google-services.json` is in the correct location
   - Check Web Client ID in `LoginViewModel.kt`

2. **Firestore Permission Denied**
   - Verify security rules are properly configured
   - Ensure user is authenticated
   - Check document ownership for write operations

3. **Gradle Sync Issues**
   - Clean and rebuild project
   - Invalidate caches and restart
   - Check Kotlin and Compose compiler versions

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- [Jetpack Compose](https://developer.android.com/jetpack/compose) for modern UI development
- [Firebase](https://firebase.google.com/) for backend services
- [Material Design](https://material.io/) for design system
- [Hilt](https://dagger.dev/hilt/) for dependency injection

## 📞 Support

For support and questions:
- Create an issue on GitHub
- Email: [adityajaif2004@example.com]
- Project URL: https://github.com/aditya0l/BuildCrew

---

**BuildCrew** - Connecting creators, one project at a time! 🚀 
