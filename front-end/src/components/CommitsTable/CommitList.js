import React from "react";
import TimeAgo from "javascript-time-ago";
import en from "javascript-time-ago/locale/en";

import OverlayTrigger from "react-bootstrap/OverlayTrigger";
import Table from "react-bootstrap/Table";
import Tooltip from "react-bootstrap/Tooltip";

import "./CommitList.css";
import Avatar from "../avatar/Avatar";

const getTimeAgo = (timeAgo, dateString) => {
  const date = new Date(dateString);
  return timeAgo.format(date);
};

const CommitList = ({ commits, handleRowClick }) => {
  TimeAgo.addLocale(en);
  const timeAgo = new TimeAgo();

  // Add locale-specific relative date/time formatting rules.
  return (
    <Table responsive hover className="commit-list">
      <thead>
        <tr className="commit-list-header">
          <th width="25%">
            <span className="header text-secondary">Author</span>
          </th>
          <th width="5%">
            <span className="header text-secondary">Commit</span>
          </th>
          <th width="60%">
            <span className="header text-secondary">Message</span>
          </th>
          <th width="10%">
            <span className="header text-secondary">Created</span>
          </th>
        </tr>
      </thead>
      <tbody>
        {commits.map((commit, key) => (
          <tr key={key} onClick={() => handleRowClick(commit)}>
            <th>
              <Avatar
                url={commit.committer.avatarUrl}
                name={commit.committer.name}
              />
            </th>

            <td className="ellipsis">
              <span>{commit.sha.substring(0, 7)}</span>
            </td>
            <td className="ellipsis">
              <span>{commit.message}</span>
            </td>
            <td className="ellipsis">
              <OverlayTrigger
                placement="top"
                overlay={
                  <Tooltip id={`tooltip-commit-date-${commit.sha}`}>
                    {new Date(commit.date).toISOString()}
                  </Tooltip>
                }
              >
                <span>{getTimeAgo(timeAgo, commit.date)}</span>
              </OverlayTrigger>
            </td>
          </tr>
        ))}
      </tbody>
    </Table>
  );
};

export default CommitList;
