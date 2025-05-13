import 'package:client/pages/settings_page.dart';
import 'package:client/utils/colors.dart';
import 'package:client/utils/text.dart';
import 'package:flutter/material.dart';

class ViberProfileHeader extends StatelessWidget {
  final String profileImageUrl;
  final String viberID;
  final String username;
  final String fullName;
  final String bio;
  final int auraPoints;
  final int viberBoards;
  final int vibeMatches;

  const ViberProfileHeader({
    super.key,
    required this.profileImageUrl,
    required this.viberID,
    required this.username,
    required this.fullName,
    required this.bio,
    required this.auraPoints,
    required this.viberBoards,
    required this.vibeMatches,
  });

  Widget _buildStat(String label, int count) {
    return Column(
      children: [
        Text(
          count.toString(),
          style: const TextStyle(
            fontSize: 18,
            fontWeight: FontWeight.bold,
            color: Colors.white,
          ),
        ),
        const SizedBox(height: 4),
        Text(label, style: const TextStyle(fontSize: 14, color: Colors.grey)),
      ],
    );
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      color: Colors.black,
      padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 20),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          // Profile Row
          Row(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              // Profile Picture
              CircleAvatar(
                radius: 60,
                backgroundImage: NetworkImage(profileImageUrl),
              ),
              const SizedBox(width: 20),
              // Stats
              Expanded(
                child: Column(
                  children: [
                    // Stats Row
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                      children: [
                        _buildStat("AuraPoints", auraPoints),
                        _buildStat("ViberBoards", viberBoards),
                        _buildStat("VibeMatches", vibeMatches),
                      ],
                    ),

                    const SizedBox(height: 16),

                    // Edit Profile Button
                    Padding(
                      padding: const EdgeInsets.symmetric(horizontal: 16.0),
                      child: SizedBox(
                        width: double.infinity, // Same width as Row above
                        child: TextButton(
                          onPressed: () {
                            Navigator.push(
                              context,
                              MaterialPageRoute(
                                builder:
                                    (context) => SettingsPage(
                                      viberID: viberID,
                                    ), // Pass userId to SettingsPage
                              ),
                            );
                          },
                          style: TextButton.styleFrom(
                            backgroundColor: Color.fromARGB(62, 141, 141, 141),
                            foregroundColor: AppColor.purple,
                            shape: RoundedRectangleBorder(
                              borderRadius: BorderRadius.circular(12),
                            ),
                            padding: const EdgeInsets.symmetric(vertical: 12),
                          ),
                          child: RText(
                            text: "Edit Profile",
                            size: 20,
                            color: Color(0xffffffff),
                          ),
                        ),
                      ),
                    ),
                  ],
                ),
              ),
            ],
          ),
          const SizedBox(height: 16),
          // Username
          Text(
            username,
            style: const TextStyle(color: Colors.white70, fontSize: 16),
          ),
          // Full Name
          Text(
            fullName,
            style: const TextStyle(
              color: Colors.white,
              fontWeight: FontWeight.bold,
              fontSize: 18,
            ),
          ),
          const SizedBox(height: 6),
          // Bio
          Text(bio, style: const TextStyle(color: Colors.white70)),
        ],
      ),
    );
  }
}
