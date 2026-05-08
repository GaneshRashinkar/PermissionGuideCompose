📱 **Android Permission Handler**
A scalable, Jetpack Compose-based implementation for managing system permissions. This flow ensures a smooth user experience by handling everything from initial requests to permanent denials.

🌟 **Key Features**
Unified Requests: Handles both single and multiple permissions seamlessly.

Rationale Support: Shows custom dialogs explaining why a permission is needed after the first decline.

Permanent Denial Handling: Automatically directs users to App Settings if they decline permissions twice.

Queue System: Manages multiple dialogs sequentially using a ViewModel to prevent UI overlapping.

🛠 **Tech Stack**
Jetpack Compose: Modern declarative UI.

MVVM Architecture: Clean separation of logic and UI.

Activity Results API: Uses ActivityResultLauncher for framework-standard handling.

Based on: The ULTIMATE Permission Handling Guide by Philipp Lackner.

https://www.youtube.com/watch?v=D3JCtaK8LSU
