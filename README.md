<h1 align="center">File Storage System</h1>



<hr>

<h2>Project Overview</h2>

<p>
The <b>Distributed File Storage System</b> is a backend-based cloud file management application inspired by platforms like Google Drive. 
The system enables users to upload, organize, share, and manage files securely while maintaining version history and storage tracking.
</p>

<p>
This project is developed using <b>Spring Boot</b> and follows a modular backend architecture with in-memory metadata handling and PostgreSQL database integration.
</p>

<hr>

<h2>Business Problem</h2>

<p>
Modern organizations and applications require a secure and scalable storage platform where users can:
</p>

<ul>
  <li>Store and organize files</li>
  <li>Share files with controlled permissions</li>
  <li>Maintain version history</li>
  <li>Track storage usage</li>
  <li>Ensure secure access control</li>
</ul>

<p>
This project simulates a distributed cloud storage system capable of handling these requirements efficiently.
</p>

<hr>

<hr>

<h2>Core Features</h2>

<h3>1. User Management</h3>

<ul>
  <li>Create and manage users</li>
  <li>Maintain unique user accounts</li>
  <li>Track user storage usage</li>
</ul>

<p><b>Implemented Functionalities:</b></p>
<img width="962" height="655" alt="Screenshot 2026-05-19 at 6 44 08 PM" src="https://github.com/user-attachments/assets/fca1bb8c-f58d-4d2b-b8a0-8a85805adf85" />


<ul>
  <li>Create User</li>
  <li>View Users</li>
</ul>

<hr>

<h3>2. Folder Management</h3>

<p>
Users can organize files into folders for better file hierarchy and management.
</p>

<p><b>Implemented Functionalities:</b></p>

<ul>
  <li>Create Folder</li>
  <li>View Folders</li>
</ul>

<hr>
<img width="948" height="662" alt="Screenshot 2026-05-19 at 6 44 23 PM" src="https://github.com/user-attachments/assets/7bd2dfdc-f72b-492e-abf5-0946ff9ce229" />

<h3>3. File Management</h3>

<p>
The system supports file creation and metadata handling similar to a cloud storage platform.
</p>

<p><b>Implemented Functionalities:</b></p>

<ul>
  <li>Create File</li>
  <li>View Files</li>
  <li>Download Simulation</li>
  <li>Large File Handling Simulation</li>
</ul>


<img width="945" height="661" alt="Screenshot 2026-05-19 at 6 44 47 PM" src="https://github.com/user-attachments/assets/cc0e49a5-c10a-4bf7-b495-b1eaf65dab4a" />

<hr>

<h3>4. File Version Control</h3>

<p>
Every file modification creates a new version, ensuring consistency and recoverability.
</p>

<p><b>Implemented Functionalities:</b></p>

<ul>
  <li>Add File Version</li>
  <li>View File Versions</li>
  <li>Maintain Version History</li>
</ul>

<hr>
<img width="959" height="679" alt="Screenshot 2026-05-19 at 6 45 20 PM" src="https://github.com/user-attachments/assets/bf33ce89-537f-43fd-9931-bda0124d21cc" />


<h3>5. File Sharing & Permissions</h3>

<p>
Files can be shared between users with controlled permission access.
</p>

<p><b>Permission Types:</b></p>

<ul>
  <li>VIEW</li>
  <li>EDIT</li>
</ul>

<p><b>Implemented Functionalities:</b></p>

<ul>
  <li>Share File</li>
  <li>View Shared Permissions</li>
  <li>Authorization Validation</li>
</ul>
<img width="818" height="659" alt="Screenshot 2026-05-19 at 6 45 46 PM" src="https://github.com/user-attachments/assets/53d5c42c-bba3-4ffe-ac13-2bc4a836a9d5" />


<hr>
<img width="954" height="682" alt="Screenshot 2026-05-19 at 6 46 08 PM" src="https://github.com/user-attachments/assets/10eb5f85-d426-4af0-ad19-71d138485c91" />


<h3>6. Storage Usage Tracking</h3>

<p>
The system monitors storage consumption for each user and enforces quota-based tracking.
</p>

<p><b>Implemented Functionalities:</b></p>

<ul>
  <li>Track Used Storage</li>
  <li>Track Available Quota</li>
  <li>Generate Storage Usage Reports</li>
</ul>

<hr>



<hr>

<h2>Business Rules</h2>

<ul>
  <li>Only authorized users can access shared files</li>
  <li>File version history must always be maintained</li>
  <li>Storage quota restrictions apply to every user</li>
  <li>Permissions determine allowed operations</li>
  <li>File retrieval should remain fast and consistent</li>
</ul>

<hr>

<h2>Database Design</h2>

<h3>Main Entities</h3>

<ul>
  <li>User</li>
  <li>Folder</li>
  <li>File</li>
  <li>FileVersion</li>
  <li>FileSharePermission</li>
</ul>

<hr>

<h2>SQL Use Cases</h2>

<ul>
  <li>Find users consuming maximum storage</li>
  <li>Find most shared files</li>
  <li>Retrieve latest file versions</li>
  <li>Track file ownership</li>
  <li>Analyze storage usage statistics</li>
</ul>

<hr>


<hr>

<h2>Console-Based Execution</h2>

<p>
The application currently runs as a console-driven Spring Boot application where users can interact through menu-based operations.
</p>

<p><b>Sample Menu:</b></p>

```text
1. Create User
2. View Users
3. Create Folder
4. View Folders
5. Create File
6. View Files
7. Add File Version
8. View File Versions
9. Share File
10. View Share Permissions
11. View Storage Report
12. Exit
```

<hr>




