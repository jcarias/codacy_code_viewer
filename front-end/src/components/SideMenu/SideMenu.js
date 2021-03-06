import React, { useState } from "react";

import "./SideMenu.css";

import { SideMenuIcons } from "./menu_icons/Icons";
import MenuItem from "./MenuItem";

const SideMenu = props => {
  const [selectedItem, setSelectedItem] = useState(1);

  const onScreenSelect = screen => {
    props.onScreenSelected(screen);
    return setSelectedItem(screen);
  };

  return (
    <div className="side-menu-root">
      <MenuItem
        icon={SideMenuIcons.DashboardIcon}
        label="Dashboard"
        selected={selectedItem === 0}
        handleSelection={() => onScreenSelect(0)}
      />
      <MenuItem
        icon={SideMenuIcons.CommitsIcon}
        label="Commits"
        selected={selectedItem === 1}
        handleSelection={() => onScreenSelect(1)}
      />
      <MenuItem
        icon={SideMenuIcons.FilesIcon}
        label="Files"
        selected={selectedItem === 2}
        handleSelection={() => onScreenSelect(2)}
      />
      <MenuItem
        icon={SideMenuIcons.IssuesIcon}
        label="Issues"
        selected={selectedItem === 3}
        handleSelection={() => onScreenSelect(3)}
      />
      <MenuItem
        icon={SideMenuIcons.PullRequestsIcon}
        label="Pull Requests"
        selected={selectedItem === 4}
        handleSelection={() => onScreenSelect(4)}
      />
      <MenuItem
        icon={SideMenuIcons.SecurityIcon}
        label="Security"
        selected={selectedItem === 5}
        handleSelection={() => onScreenSelect(5)}
      />
    </div>
  );
};

export default SideMenu;
