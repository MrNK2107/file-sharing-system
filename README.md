<h1 align="center">Distributed File Storage System</h1>

<h3 align="center">
Google Drive-like File Management System using Spring Boot
</h3>

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

<h2>Technology Stack</h2>

<table border="1" cellpadding="10">
  <tr>
    <th>Technology</th>
    <th>Purpose</th>
  </tr>
  <tr>
    <td>Java</td>
    <td>Core Programming Language</td>
  </tr>
  <tr>
    <td>Spring Boot</td>
    <td>Backend Framework</td>
  </tr>
  <tr>
    <td>Spring Data JPA</td>
    <td>ORM & Database Operations</td>
  </tr>
  <tr>
    <td>PostgreSQL</td>
    <td>Relational Database</td>
  </tr>
  <tr>
    <td>Hibernate</td>
    <td>Entity Mapping</td>
  </tr>
  <tr>
    <td>Maven</td>
    <td>Dependency Management</td>
  </tr>
</table>

<hr>

<h2>Core Features</h2>

<h3>1. User Management</h3>

<ul>
  <li>Create and manage users</li>
  <li>Maintain unique user accounts</li>
  <li>Track user storage usage</li>
</ul>

<p><b>Implemented Functionalities:</b></p>
<img width="1680" height="1050" alt="Screenshot 2026-05-19 at 6 44 08 PM" src="https://github.com/user-attachments/assets/01325860-5e7c-45de-9f29-71d88b82789b" />

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
<img width="1680" height="1050" alt="Screenshot 2026-05-19 at 6 44 23 PM" src="https://github.com/user-attachments/assets/6237a00f-7769-4498-888b-1143fd2aab7b" />
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


<img width="1680" height="1050" alt="Screenshot 2026-05-19 at 6 44 47 PM" src="https://github.com/user-attachments/assets/d0f27cbf-117c-48f1-9afc-33b20eb3e93c" />

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
<img width="1680" height="1050" alt="Screenshot 2026-05-19 at 6 45 09 PM" src="https://github.com/user-attachments/assets/1f7b278a-49d1-4dcc-91c9-3562c6b40871" />

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
<img width="1680" height="1050" alt="Screenshot 2026-05-19 at 6 45 46 PM" src="https://github.com/user-attachments/assets/3ba7d753-0a24-42be-8662-3e52636734d1" />

<hr>
<img width="1680" height="1050" alt="Screenshot 2026-05-19 at 6 46 08 PM" src="https://github.com/user-attachments/assets/4c4d10b7-7349-474c-919f-998f2e8d90f8" />

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

<h2>Main System Flows</h2>

<table border="1" cellpadding="10">
  <tr>
    <th>Flow</th>
    <th>Description</th>
  </tr>
  <tr>
    <td>User Authentication</td>
    <td>User validation and access control</td>
  </tr>
  <tr>
    <td>File Upload</td>
    <td>Store file metadata and versions</td>
  </tr>
  <tr>
    <td>File Download</td>
    <td>Retrieve files quickly</td>
  </tr>
  <tr>
    <td>Sharing & Permissions</td>
    <td>Grant controlled file access</td>
  </tr>
  <tr>
    <td>Version Management</td>
    <td>Maintain consistent file history</td>
  </tr>
  <tr>
    <td>Storage Tracking</td>
    <td>Monitor user storage usage</td>
  </tr>
</table>

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

<h2>Project Structure</h2>

```bash
src
 └── main
      ├── java
      │     ├── controller
      │     ├── service
      │     ├── repository
      │     ├── entity
      │     └── dto
      │
      └── resources
            └── application.properties
```

<hr>

<h2>Application Configuration</h2>

<p>
The project uses PostgreSQL database configuration through the 
<code>application.properties</code> file.
</p>

<p><b>Example Configuration:</b></p>

```properties
spring.datasource.url=YOUR_DATABASE_URL
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
```

<hr>

<h2>Sample Functionalities Implemented</h2>

<ul>
  <li>Create User</li>
  <li>Create Folder</li>
  <li>Create File</li>
  <li>Add File Versions</li>
  <li>Share Files</li>
  <li>View Share Permissions</li>
  <li>Generate Storage Reports</li>
</ul>

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

<h2>Screenshots</h2>

<p>
Add screenshots of:
</p>

<ul>
  <li>User Creation</li>
  <li>Folder Management</li>
  <li>File Upload</li>
  <li>Version Control</li>
  <li>Permission Sharing</li>
  <li>Storage Report</li>
</ul>

<p>
Example:
</p>

```html
<img src="screenshots/storage-report.png" width="800"/>
```

<hr>

<h2>Future Enhancements</h2>

<ul>
  <li>JWT Authentication</li>
  <li>REST API Integration</li>
  <li>Frontend using React</li>
  <li>AWS S3 File Storage</li>
  <li>Distributed File Replication</li>
  <li>File Compression</li>
  <li>Role-Based Access Control</li>
  <li>Real-Time Notifications</li>
</ul>

<hr>

<h2>Learning Outcomes</h2>

<ul>
  <li>Spring Boot Backend Development</li>
  <li>Database Design & Relationships</li>
  <li>JPA & Hibernate ORM</li>
  <li>File Metadata Management</li>
  <li>Version Control Logic</li>
  <li>Permission-Based Authorization</li>
  <li>Storage Tracking Systems</li>
</ul>

<hr>

<h2>Conclusion</h2>

<p>
This project demonstrates the backend architecture and core functionalities of a distributed cloud storage platform using Spring Boot. 
It focuses on scalability concepts, secure file sharing, version management, and storage tracking while simulating real-world cloud storage operations.
</p>

<hr>

<h2>Author</h2>

<p>
Developed using <b>Spring Boot</b> and <b>PostgreSQL</b> as part of a backend system design and database management project.
</p>
