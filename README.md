TravelingJournal-Web/
├── src/
│   └── main/
│       ├── java/
│       │   └── com.travelingjournal/
│       │       ├── servlet/                  # All Servlets (controllers)
│       │       │   ├── HomeServlet.java
│       │       │   ├── LoginServlet.java
│       │       │   ├── RegisterServlet.java
│       │       │   ├── TripServlet.java          # List, add, edit trips
│       │       │   ├── EntryServlet.java         # Add/view journal entries
│       │       │   ├── PhotoUploadServlet.java   # Handles file upload
│       │       │   └── LogoutServlet.java
│       │       │
│       │       ├── service/                      # Business logic
│       │       │   ├── UserService.java
│       │       │   ├── TripService.java
│       │       │   └── EntryService.java
│       │       │
│       │       ├── dao/                          # Data Access (JDBC)
│       │       │   ├── UserDAO.java
│       │       │   ├── TripDAO.java
│       │       │   └── EntryDAO.java
│       │       │
│       │       ├── model/                        # POJOs
│       │       │   ├── User.java
│       │       │   ├── Trip.java
│       │       │   └── JournalEntry.java
│       │       │
│       │       ├── util/
│       │       │   ├── DatabaseUtil.java         # Gets Connection
│       │       │   └── PasswordUtil.java         # Hashing (BCrypt)
│       │       │
│       │       └── filter/
│       │           └── AuthFilter.java           # Protects pages after login
│       │
│       ├── webapp/
│       │   ├── WEB-INF/
│       │   │   ├── web.xml                       # Servlet mapping
│       │   │   └── lib/                          # (if needed)
│       │   │
│       │   ├── css/
│       │   │   └── style.css
│       │   ├── js/
│       │   │   └── script.js
│       │   ├── images/
│       │   │   └── uploads/                      # Photos go here (create manually)
│       │   │
│       │   ├── jsp/                              # All JSP pages (your HTML views)
│       │   │   ├── layout/
│       │   │   │   ├── header.jsp
│       │   │   │   └── footer.jsp
│       │   │   │
│       │   │   ├── auth/
│       │   │   │   ├── login.jsp
│       │   │   │   └── register.jsp
│       │   │   │
│       │   │   ├── trip/
│       │   │   │   ├── list.jsp                  # All my trips
│       │   │   │   ├── view.jsp                  # Trip detail + entries
│       │   │   │   ├── add.jsp
│       │   │   │   └── edit.jsp
│       │   │   │
│       │   │   ├── entry/
│       │   │   │   ├── add.jsp
│       │   │   │   └── edit.jsp
│       │   │   │
│       │   │   └── home.jsp                      # Dashboard after login
│       │   │
│       │   └── index.jsp                         # Redirects to login or home
│       │
│       └── resources/
│           └── db/
│               └── travel_journal.sql            # Database schema
│
├── pom.xml                                       # Maven (recommended)
└── README.md




