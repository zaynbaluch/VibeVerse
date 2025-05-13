import 'dart:io';

import 'package:client/components/discord.dart';
import 'package:client/utils/colors.dart';
import 'package:client/utils/headings.dart';
import 'package:client/utils/text.dart';
import 'package:flutter/material.dart';
import 'package:client/components/login.dart';
import 'package:client/components/sign_up.dart';

class LandingPage extends StatefulWidget {
  const LandingPage({super.key});

  @override
  State<LandingPage> createState() => _LandingPageState();
}

class _LandingPageState extends State<LandingPage> {
  bool showLogin = true;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Stack(
        children: [
          Positioned.fill(
            child: Image.asset('images/background1.png', fit: BoxFit.cover),
          ),
          Center(
            child: Container(
              height: 500,
              width: 800,
              decoration: BoxDecoration(
                color: const Color.fromARGB(255, 60, 60, 60),
                borderRadius: BorderRadius.circular(24),
                boxShadow: [
                  BoxShadow(
                    color: Colors.black38,
                    blurRadius: 20,
                    offset: Offset(0, 10),
                  ),
                ],
              ),
              padding: const EdgeInsets.all(24),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: [
                  // Left side: buttons and form
                  Column(
                    children: [
                      Row(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          Row(
                            mainAxisAlignment: MainAxisAlignment.center,
                            children: [
                              SizedBox(
                                width: 150,
                                child: TextButton(
                                  onPressed: () {
                                    setState(() {
                                      showLogin = true;
                                    });
                                  },
                                  style: TextButton.styleFrom(
                                    backgroundColor:
                                        showLogin
                                            ? AppColor.purple
                                            : Colors.transparent,
                                    foregroundColor: Colors.white,
                                    shape: const RoundedRectangleBorder(
                                      borderRadius: BorderRadius.zero,
                                    ),
                                    padding: EdgeInsets.zero,
                                  ),
                                  child: RText(
                                    text: 'Login',
                                    color: AppColor.white,
                                  ),
                                ),
                              ),

                              SizedBox(
                                width: 150,
                                child: TextButton(
                                  onPressed: () {
                                    setState(() {
                                      showLogin = false;
                                    });
                                  },
                                  style: TextButton.styleFrom(
                                    backgroundColor:
                                        !showLogin
                                            ? AppColor.purple
                                            : Colors.transparent,
                                    foregroundColor: Colors.white,
                                    shape: const RoundedRectangleBorder(
                                      borderRadius: BorderRadius.zero,
                                    ),
                                    padding: EdgeInsets.zero,
                                  ),
                                  child: RText(
                                    text: 'SignUp',
                                    color: AppColor.white,
                                  ),
                                ),
                              ),
                            ],
                          ),
                        ],
                      ),

                      Flexible(
                        child: SizedBox(
                          width: 300,
                          height: 400,
                          child:
                              showLogin
                                  ? Center(child: const Login())
                                  : const SignUp(),
                        ),
                      ),
                    ],
                  ),

                  // Right side: Vibeverse content
                  Column(
                    mainAxisAlignment: MainAxisAlignment.center,

                    children: [
                      HeaderText(
                        text: "VIBEVERSE",
                        color: AppColor.white,
                        size: 70,
                      ),
                      RText(
                        text: 'One vibesite for all your vibes',
                        color: Color.fromARGB(255, 128, 128, 128),
                      ),
                      Discord(),
                    ],
                  ),
                ],
              ),
            ),
          ),
        ],
      ),
    );
  }
}
