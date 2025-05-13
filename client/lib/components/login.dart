import 'package:client/pages/profile_page.dart';
import 'package:client/utils/colors.dart';
import 'package:client/utils/text.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'package:animated_splash_screen/animated_splash_screen.dart';

class Login extends StatefulWidget {
  const Login({super.key});

  @override
  State<Login> createState() => _LoginPageState();
}

class _LoginPageState extends State<Login> {
  final _formKey = GlobalKey<FormState>();

  final TextEditingController emailController = TextEditingController();
  final TextEditingController passwordController = TextEditingController();

  bool isLoading = false;

  Future<void> login() async {
    if (_formKey.currentState!.validate()) {
      setState(() => isLoading = true);

      final Map<String, dynamic> loginData = {
        "email": emailController.text,
        "password": passwordController.text,
      };

      final response = await http.post(
        Uri.parse('http://localhost:8080/api/vibers/login'),
        headers: {'Content-Type': 'application/json'},
        body: json.encode(loginData),
      );

      setState(() => isLoading = false);

      if (response.statusCode == 200) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('Login successful!'),
            backgroundColor: AppColor.green,
          ),
        );
        // TODO: Navigate to the home page or save token
      } else {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('Invalid ViberTag or password'),
            backgroundColor: AppColor.red,
          ),
        );
      }
    }
  }

  InputDecoration _inputDecoration(String label) {
    return InputDecoration(
      labelText: label,
      labelStyle: const TextStyle(color: Colors.grey),
      enabledBorder: const UnderlineInputBorder(
        borderSide: BorderSide(color: Colors.grey),
      ),
      focusedBorder: UnderlineInputBorder(
        borderSide: BorderSide(color: AppColor.green),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      color: Colors.transparent,
      width: 400,
      padding: const EdgeInsets.all(24),
      child: Form(
        key: _formKey,
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            TextFormField(
              controller: emailController,
              style: const TextStyle(color: Color.fromARGB(255, 255, 255, 255)),
              decoration: _inputDecoration('ViberTag'),
              validator:
                  (value) => value == null ? 'Enter valid username' : null,
            ),
            TextFormField(
              controller: passwordController,
              obscureText: true,
              style: const TextStyle(color: Color.fromARGB(255, 255, 255, 255)),
              decoration: _inputDecoration('Password'),
              validator:
                  (value) =>
                      value == null || value.length < 6
                          ? 'Minimum 6 characters'
                          : null,
            ),
            const SizedBox(height: 20),
            isLoading
                ? const CircularProgressIndicator()
                : ElevatedButton(
                  onPressed: () {
                    Navigator.pushReplacement(
                      context,
                      MaterialPageRoute(
                        builder: (context) => VibeSplashScreen(),
                      ),
                    );
                  },
                  style: ElevatedButton.styleFrom(
                    backgroundColor: AppColor.green,
                    foregroundColor: Colors.white,
                  ),
                  child: RText(text: 'Enter VibeVerse'),
                ),
          ],
        ),
      ),
    );
  }
}

class VibeSplashScreen extends StatelessWidget {
  const VibeSplashScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return AnimatedSplashScreen(
      duration: 1500,
      splash: Center(
        child: Column(
          children: [
            Image.asset('images/logo.png', width: 60, fit: BoxFit.cover),
            RText(text: "VIBEVERSE", size: 30),
          ],
        ),
      ),
      nextScreen: const ProfilePage(username: 'libero31'),
      splashTransition: SplashTransition.scaleTransition,
      backgroundColor: AppColor.green,
    );
  }
}
