Requirements for a Mobile App: Lucid

Short Description
We will create a mobile app that allows users to track and document their dreams. The app will help users record important details about their dreams, mark whether the dream was lucid (a dream in which the dreamer is aware that they are dreaming), and review their dream history. Users can also update or delete dream entries.

The app will work offline, storing dream entries locally and syncing them with a server when the user reconnects to the internet.

Domain Details
Here's the main entity the app will store and manage:

Dream
Title: A short, descriptive title of the dream (e.g., "Flying Over a City").
Date: The date when the dream occurred.
Lucid: A yes/no field indicating whether the dream was lucid or not.
Description: A detailed account of the dream, allowing users to describe what they remember.
Tags: Keywords that describe the dream's themes (e.g., flying, fear, joy).
Mood: How the user felt during or after the dream (e.g., happy, anxious, confused).
Location: (Optional) Where the dream took place (if relevant).
People: (Optional) Who was present in the dream (e.g., family, friends, strangers).

CRUD Operations
CRUD stands for Create, Read, Update, and Delete. These are the core actions users can perform on their dream entries.

1. Create
This action allows the user to log a new dream entry.
Example: The user had a dream last night and opens the app to log the details. They enter the dream title, select whether the dream was lucid, and write a description.
Action: The app saves this information in the local database and syncs it with the server when the device is online.

2. Read
This action allows users to view their previously logged dreams.
Example: The user browses their dream history to revisit a dream from last month. They can filter dreams by date, lucidity, or tags.
Action: The app retrieves dream data either from the local database (if offline) or from the server (if online).

3. Edit
This action allows users to modify the details of a previously logged dream.
Example: The user realizes they forgot an important detail about a dream they logged earlier. They can update the dream description or change the lucid flag.
Action: The updated dream details are stored locally first and then synchronized with the server when internet access is available.

4. Delete
This action allows users to remove dream entries they no longer want.
Example: The user wants to delete a dream entry that was logged by mistake.
Action: The app deletes the dream from the local database first, and then the deletion is reflected on the server when online.

Persistence Details
The app will store dream data both locally and on a remote server. This ensures that users can access their dream logs whether they are online or offline. Here's how each CRUD operation interacts with the local and server databases:

Create: New dreams are stored locally on the phone first and then sent to the server for remote backup and access when the device is connected to the internet.
Read: When online, the app retrieves dream data from the server. When offline, it retrieves data from the local database.
Update: Updates to dream entries are stored locally first, then synced with the server once the internet is available.
Delete: Deleted dream entries are removed from the local storage first, then the deletion is synced with the server.

Offline Behavior
When the device is offline, the app will continue to function seamlessly by using locally stored dream data. All actions logging new dreams, viewing dream history, updating, or deleting dreams will happen on the local database. Once the device reconnects to the internet, all changes made while offline will be automatically synced to the server.

Offline Scenarios:
Scenario 1: The user logs a new dream while traveling and is offline. The dream is stored locally and uploaded to the server when the internet connection is restored.
Scenario 2: The user browses their dream history while offline. The app displays locally stored dream data.
Scenario 3: The user deletes an old dream entry while offline. The deletion is processed locally and synced to the server once online again.
