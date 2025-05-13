import 'package:client/components/profile.dart';
import 'package:client/components/vibeboard_display.dart';
import 'package:client/components/vibejournal_display.dart';
import 'package:client/main_scaffold.dart';
import 'package:client/utils/colors.dart';
import 'package:client/utils/text.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

class ProfilePage extends StatefulWidget {
  final String username;

  const ProfilePage({super.key, required this.username});

  @override
  State<ProfilePage> createState() => _ProfilePageState();
}

class _ProfilePageState extends State<ProfilePage> {
  late Future<Map<String, dynamic>> profileData;
  bool showBoards = true;
  String? userId;

  @override
  void initState() {
    super.initState();
    profileData = fetchProfileData(widget.username);
  }

  Future<Map<String, dynamic>> fetchProfileData(String username) async {
    final response = await http.get(
      Uri.parse('http://localhost:8080/api/vibers/username/$username'),
    );

    if (response.statusCode == 200) {
      // Parse the response body into a Map
      Map<String, dynamic> data = json.decode(response.body);

      return data;
    } else {
      throw Exception('Failed to load profile');
    }
  }

  @override
  Widget build(BuildContext context) {
    return MainScaffold(
      currentRoute: 'profile',
      onNavigate: (route) {
        // Replace this with your own navigator logic
        Navigator.pushReplacementNamed(context, '/$route');
      },

      body: FutureBuilder<Map<String, dynamic>>(
        future: profileData,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          }

          if (snapshot.hasError) {
            return Center(child: Text("Error: ${snapshot.error}"));
          }

          final data = snapshot.data!;
          return Container(
            padding: const EdgeInsets.all(16),
            constraints: BoxConstraints(
              minHeight: MediaQuery.of(context).size.height,
            ),
            child: SingleChildScrollView(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                mainAxisAlignment: MainAxisAlignment.start,
                children: [
                  // Profile Section
                  ViberProfileHeader(
                    profileImageUrl:
                        data['profilePicture'] ??
                        'https://i.postimg.cc/zXZ8wfY6/profile.jpg',
                    viberID: data['id'],
                    username: "@${data['username']}",
                    fullName: "${data['firstName']} ${data['lastName']}",
                    bio: data['bio'] ?? 'No bio set',
                    auraPoints: data['auraPoints'] ?? 200,
                    viberBoards: data['vibeBoards'] ?? 2,
                    vibeMatches: data['vibeMatches'] ?? 6,
                  ),
                  const SizedBox(height: 30),

                  // Toggle Buttons
                  Row(
                    mainAxisAlignment: MainAxisAlignment.start,
                    children: [
                      _buildToggleButton('VibeBoards', showBoards, () {
                        setState(() => showBoards = true);
                      }),
                      _buildToggleButton('VibeJournal', !showBoards, () {
                        setState(() => showBoards = false);
                      }),
                    ],
                  ),
                  const SizedBox(height: 20),

                  // Display Section
                  Container(
                    width: double.infinity,
                    padding: const EdgeInsets.all(16),
                    decoration: BoxDecoration(
                      color: const Color.fromARGB(150, 0, 0, 0),
                      borderRadius: BorderRadius.circular(10),
                    ),
                    child:
                        showBoards
                            ? const VibeboardDisplay()
                            : const VibejournalDisplay(),
                  ),
                ],
              ),
            ),
          );
        },
      ),
    );
  }

  Widget _buildToggleButton(
    String label,
    bool isActive,
    VoidCallback onPressed,
  ) {
    return Expanded(
      child: TextButton(
        onPressed: onPressed,
        style: TextButton.styleFrom(
          backgroundColor: isActive ? AppColor.purple : Colors.transparent,
          foregroundColor: AppColor.white,
          shape: const RoundedRectangleBorder(borderRadius: BorderRadius.zero),
        ),
        child: RText(text: label, color: AppColor.white),
      ),
    );
  }
}
