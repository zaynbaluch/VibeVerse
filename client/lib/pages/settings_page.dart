import 'dart:convert';

import 'package:client/utils/text.dart';
import 'package:flutter/material.dart';
import 'package:client/main_scaffold.dart';
import 'package:client/utils/colors.dart';
import 'package:http/http.dart' as http;

class SettingsPage extends StatefulWidget {
  final String viberID;
  const SettingsPage({super.key, required this.viberID});

  @override
  State<SettingsPage> createState() => _SettingsPageState();
}

class _SettingsPageState extends State<SettingsPage> {
  late Future<Map<String, dynamic>> profileData;
  late TextEditingController usernameController;
  late TextEditingController emailController;
  late TextEditingController firstNameController;
  late TextEditingController lastNameController;
  late TextEditingController dobController;
  late TextEditingController bioController;
  late TextEditingController profileUrlController;
  late TextEditingController passwordController;
  bool isLoading = false;
  bool _obscurePassword = true;

  @override
  void initState() {
    super.initState();
    usernameController = TextEditingController();
    emailController = TextEditingController();
    firstNameController = TextEditingController();
    lastNameController = TextEditingController();
    dobController = TextEditingController();
    bioController = TextEditingController();
    profileUrlController = TextEditingController();
    passwordController = TextEditingController();
    profileData = fetchProfileData(widget.viberID);
  }

  @override
  void dispose() {
    usernameController.dispose();
    emailController.dispose();
    firstNameController.dispose();
    lastNameController.dispose();
    dobController.dispose();
    bioController.dispose();
    profileUrlController.dispose();
    passwordController.dispose();
    super.dispose();
  }

  Future<Map<String, dynamic>> fetchProfileData(String viberID) async {
    try {
      final response = await http.get(
        Uri.parse('http://localhost:8080/api/vibers/$viberID'),
      );

      if (response.statusCode == 200) {
        Map<String, dynamic> data = json.decode(response.body);

        // Populate the TextEditingControllers with existing profile data
        // Using null-aware operators to handle null values
        setState(() {
          usernameController.text = data['username'] ?? '';
          emailController.text = data['email'] ?? '';
          firstNameController.text = data['firstName'] ?? '';
          lastNameController.text = data['lastName'] ?? '';
          dobController.text = data['dob'] ?? '';
          bioController.text = data['bio'] ?? '';
          profileUrlController.text = data['profileUrl'] ?? '';
          // Don't set password from API for security reasons
        });

        return data;
      } else {
        throw Exception('Failed to load profile: ${response.statusCode}');
      }
    } catch (e) {
      throw Exception('Failed to load profile: $e');
    }
  }

  Future<void> updateProfile() async {
    setState(() {
      isLoading = true;
    });

    // Validate password is not empty
    if (passwordController.text.isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text('Password is required to update your profile'),
          backgroundColor: AppColor.red,
        ),
      );
      setState(() {
        isLoading = false;
      });
      return;
    }

    try {
      final updatedData = {
        'username': usernameController.text,
        'email': emailController.text,
        'firstName': firstNameController.text,
        'lastName': lastNameController.text,
        'dob': dobController.text,
        'bio': bioController.text,
        'profileUrl': profileUrlController.text,
        'password': passwordController.text,
      };

      final response = await http.put(
        Uri.parse('http://localhost:8080/api/vibers/${widget.viberID}'),
        headers: {'Content-Type': 'application/json'},
        body: json.encode(updatedData),
      );

      if (response.statusCode == 200) {
        // Clear password field after successful update
        passwordController.clear();

        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('Profile updated successfully'),
            backgroundColor: AppColor.green
          ),
        );
        setState(() {
          profileData = fetchProfileData(widget.viberID);
        });
      } else {
        String errorMessage =
            'Failed to update profile: ${response.statusCode}';
        try {
          final errorBody = json.decode(response.body);
          if (errorBody['message'] != null) {
            errorMessage = errorBody['message'];
          }
        } catch (_) {}

        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text(errorMessage), backgroundColor: AppColor.red),
        );
      }
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text('Error updating profile: $e'),
          backgroundColor: AppColor.red,
        ),
      );
    } finally {
      setState(() {
        isLoading = false;
      });
    }
  }

  Widget _buildTextField(
    String label,
    TextEditingController controller, {
    int maxLines = 1,
    bool isPassword = false,
    String? hintText,
    IconData? prefixIcon,
  }) {
    return Padding(
      padding: const EdgeInsets.only(bottom: 20),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text(
            label,
            style: TextStyle(
              fontWeight: FontWeight.bold,
              color: AppColor.white,
              fontSize: 16,
            ),
          ),
          const SizedBox(height: 8),
          TextField(
            controller: controller,
            maxLines: maxLines,
            obscureText: isPassword && _obscurePassword,
            style: TextStyle(color: AppColor.white),
            decoration: InputDecoration(
              filled: true,
              fillColor: Colors.grey[800],
              hintText: hintText,
              hintStyle: TextStyle(color: Colors.grey[400]),
              border: OutlineInputBorder(
                borderRadius: BorderRadius.circular(8),
                borderSide: BorderSide.none,
              ),
              contentPadding: const EdgeInsets.symmetric(
                horizontal: 16,
                vertical: 12,
              ),
              prefixIcon:
                  prefixIcon != null
                      ? Icon(prefixIcon, color: Colors.grey[400])
                      : null,
              suffixIcon:
                  isPassword
                      ? IconButton(
                        icon: Icon(
                          _obscurePassword
                              ? Icons.visibility_off
                              : Icons.visibility,
                          color: Colors.grey[400],
                        ),
                        onPressed: () {
                          setState(() {
                            _obscurePassword = !_obscurePassword;
                          });
                        },
                      )
                      : null,
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildProfileField(String label, String? value) {
    return Padding(
      padding: const EdgeInsets.only(bottom: 16),
      child: Row(
        children: [
          Text(
            "$label: ",
            style: TextStyle(
              fontWeight: FontWeight.bold,
              color: AppColor.white,
              fontSize: 16,
            ),
          ),
          Text(
            value ?? "Not available",
            style: TextStyle(color: AppColor.white, fontSize: 16),
          ),
        ],
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return MainScaffold(
      currentRoute: 'settings',
      onNavigate: (route) {
        Navigator.pushReplacementNamed(context, '/$route');
      },
      body: Container(
        padding: const EdgeInsets.all(16),
        constraints: BoxConstraints(
          minHeight: MediaQuery.of(context).size.height,
        ),
        child: Center(
          // Center the content
          child: SingleChildScrollView(
            child: FutureBuilder<Map<String, dynamic>>(
              future: profileData,
              builder: (context, snapshot) {
                if (snapshot.connectionState == ConnectionState.waiting) {
                  return Center(child: CircularProgressIndicator());
                } else if (snapshot.hasError) {
                  return Center(
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        Text(
                          "Error loading profile",
                          style: TextStyle(color: AppColor.white, fontSize: 18),
                        ),
                        SizedBox(height: 8),
                        Text(
                          "${snapshot.error}",
                          style: TextStyle(color: Colors.red, fontSize: 14),
                        ),
                        SizedBox(height: 16),
                        ElevatedButton(
                          onPressed: () {
                            setState(() {
                              profileData = fetchProfileData(widget.viberID);
                            });
                          },
                          child: Text("Retry"),
                          style: ElevatedButton.styleFrom(
                            backgroundColor: AppColor.purple,
                            padding: EdgeInsets.symmetric(
                              horizontal: 24,
                              vertical: 12,
                            ),
                          ),
                        ),
                      ],
                    ),
                  );
                } else if (!snapshot.hasData) {
                  return Center(
                    child: Text(
                      "No data found",
                      style: TextStyle(color: AppColor.white),
                    ),
                  );
                } else {
                  return ConstrainedBox(
                    constraints: BoxConstraints(
                      maxWidth: 500,
                    ), // Constrain width for better layout
                    child: Card(
                      color: Colors.grey[900],
                      elevation: 8,
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(16),
                      ),
                      child: Padding(
                        padding: const EdgeInsets.all(24),
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.center,
                          children: [
                            // Profile header
                            RText(
                              text: "EDIT PROFILE",
                              color: AppColor.white,
                              size: 28,
                            ),

                            Divider(color: Colors.grey[700], height: 40),

                            

                            // Profile form fields
                            _buildTextField(
                              "Profile URL",
                              profileUrlController,
                              hintText: "Enter profile image URL",
                              prefixIcon: Icons.image,
                            ),
                            _buildTextField(
                              "Username",
                              usernameController,
                              hintText: "Enter username",
                              prefixIcon: Icons.person,
                            ),
                            _buildTextField(
                              "First Name",
                              firstNameController,
                              hintText: "Enter first name",
                              prefixIcon: Icons.badge,
                            ),
                            _buildTextField(
                              "Last Name",
                              lastNameController,
                              hintText: "Enter last name",
                              prefixIcon: Icons.badge,
                            ),
                            _buildTextField(
                              "Email",
                              emailController,
                              hintText: "Enter email address",
                              prefixIcon: Icons.email,
                            ),
                            _buildTextField(
                              "Date of Birth",
                              dobController,
                              hintText: "YYYY-MM-DD",
                              prefixIcon: Icons.calendar_today,
                            ),
                            _buildTextField(
                              "Bio",
                              bioController,
                              maxLines: 3,
                              hintText: "Tell us about yourself",
                              prefixIcon: Icons.info,
                            ),
                            _buildTextField(
                              "Password",
                              passwordController,
                              isPassword: true,
                              hintText: "Enter your password to save changes",
                              prefixIcon: Icons.lock,
                            ),

                            SizedBox(height: 24),

                            // Update button
                            SizedBox(
                              width: double.infinity,
                              child: ElevatedButton(
                                onPressed: isLoading ? null : updateProfile,
                                style: ElevatedButton.styleFrom(
                                  backgroundColor: AppColor.purple,
                                  padding: EdgeInsets.symmetric(vertical: 16),
                                  shape: RoundedRectangleBorder(
                                    borderRadius: BorderRadius.circular(8),
                                  ),
                                  disabledBackgroundColor: Colors.grey,
                                ),
                                child:
                                    isLoading
                                        ? SizedBox(
                                          height: 20,
                                          width: 20,
                                          child: CircularProgressIndicator(
                                            color: AppColor.white,
                                            strokeWidth: 2,
                                          ),
                                        )
                                        : Text(
                                          "Update Profile",
                                          style: TextStyle(
                                            color: AppColor.white,
                                            fontSize: 16,
                                            fontWeight: FontWeight.bold,
                                          ),
                                        ),
                              ),
                            ),
                          ],
                        ),
                      ),
                    ),
                  );
                }
              },
            ),
          ),
        ),
      ),
    );
  }
}
