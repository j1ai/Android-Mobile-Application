# project-team-16

## use the *strings.xml* located in *res/values* instead of hardcoding literals

## problem:
menu_activity.xml is a drawerlayout that builds the navigation menu, this activity includes app_bar_menu.xml which is the toolbar, and then app_bar_menu.xml includes the content_menu.xml which is the actual content of the main page. The sensible thing to do is to reuse an existing toolbar with the navigation menu, but I don't see a way this can be done with the current structure.. how can you grab the navigation menu if it's the parent of 2 other XML activities (you'll just end up with all the unnecessary content). 

## right now: menu_activity.xml -> app_bar_menu.xml -> content_menu.xml

## need: content_menu.xml -> app_bar_menu.xml -> menu_activity.xml

note: I don't think creating a whole new navigation bar for each new page is a good idea, suggestions are welcome on how to fix this issue (redo the menu activity entirely?)
