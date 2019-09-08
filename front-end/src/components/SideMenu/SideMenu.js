import React, { useState } from "react";

import "./SideMenu.css";

import { SideMenuIcons } from "./menu_icons/Icons";
import MenuItem from "./MenuItem";

const SideMenu = props => {
  const [selectedItem, setSelectedItem] = useState(1);

  return (
    <div className="side-menu-root">
      <MenuItem
        icon={SideMenuIcons.DashboardIcon}
        label="Dashboard"
        selected={selectedItem === 0}
        handleSelection={() => setSelectedItem(0)}
      />
      <MenuItem
        icon={SideMenuIcons.CommitsIcon}
        label="Commits"
        selected={selectedItem === 1}
        handleSelection={() => setSelectedItem(1)}
      />
      <MenuItem
        icon={SideMenuIcons.FilesIcon}
        label="Files"
        selected={selectedItem === 2}
        handleSelection={() => setSelectedItem(2)}
      />
      <MenuItem
        icon={SideMenuIcons.IssuesIcon}
        label="Issues"
        selected={selectedItem === 3}
        handleSelection={() => setSelectedItem(3)}
      />
      <MenuItem
        icon={SideMenuIcons.PullRequestsIcon}
        label="Pull Requests"
        selected={selectedItem === 4}
        handleSelection={() => setSelectedItem(4)}
      />
      <MenuItem
        icon={SideMenuIcons.SecurityIcon}
        label="Security"
        selected={selectedItem === 5}
        handleSelection={() => setSelectedItem(5)}
      />
    </div>
  );
};

export default SideMenu;
