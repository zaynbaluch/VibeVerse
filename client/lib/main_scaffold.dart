import 'package:client/utils/headings.dart';
import 'package:client/utils/text.dart';
import 'package:flutter/material.dart';
import 'package:client/utils/colors.dart';

class MainScaffold extends StatelessWidget {
  final Widget body;
  final String currentRoute;
  final Function(String route) onNavigate;

  const MainScaffold({
    super.key,
    required this.body,
    required this.currentRoute,
    required this.onNavigate,
  });

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Stack(
        children: [
          Positioned.fill(
            child: Image.asset('images/background1.png', fit: BoxFit.cover),
          ),
          Row(
            children: [
              // Main content area
              Expanded(child: SafeArea(top: false, child: body)),

              // Right Side Navigation
              Container(
                width: 80,
                color: AppColor.green,
                child: Column(
                  children: [
                    const SizedBox(height: 10),
                    _navIcon(
                      Icons.home,
                      "Home",
                      currentRoute == 'home',
                      () => onNavigate("home"),
                    ),
                    _navIcon(
                      Icons.explore,
                      "Explore",
                      currentRoute == 'explore',
                      () => onNavigate("explore"),
                    ),
                    _navIcon(
                      Icons.person,
                      "Profile",
                      currentRoute == 'profile',
                      () => onNavigate("profile"),
                    ),
                    _navIcon(
                      Icons.add_circle,
                      "Create",
                      currentRoute == 'create',
                      () => onNavigate("create"),
                    ),
                    const Spacer(),

                    const SizedBox(height: 5),
                    HeaderText(text: "VIBEVERSE", size: 20),
                    const SizedBox(height: 10),
                  ],
                ),
              ),
            ],
          ),
        ],
      ),
    );
  }

  Widget _navIcon(
    IconData icon,
    String tooltip,
    bool selected,
    VoidCallback onTap,
  ) {
    return IconButton(
      icon: Icon(
        icon,
        color: selected ? Colors.black : Colors.black26,
        size: 30,
      ),
      tooltip: tooltip,
      onPressed: onTap,
    );
  }
}
