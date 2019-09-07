import React from "react";

import IconCommits from "./menu_icons/icon-commits.svg";
import IconDashboard from "./menu_icons/icon-dashboard.svg";

import "./SideMenu.css";
const SideMenu = props => {
  return (
    <div className="side-menu-root">
      <span>This is the menu</span>
      <div>
        <img src={IconCommits} alt="" />
      </div>
      <div>
        <img src={IconDashboard} alt="" />
      </div>
    </div>
  );
};

export default SideMenu;
