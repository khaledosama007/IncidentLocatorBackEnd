placesSET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


--
-- Table structure for table `places`
--

CREATE TABLE IF NOT EXISTS `places` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(300) NOT NULL,
  `description` text NOT NULL,
  `lat` double NOT NULL,
  `long` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(300) NOT NULL,
  `email` varchar(500) NOT NULL,
  `password` varchar(300) NOT NULL,
  `lat` double DEFAULT NULL,
  `long` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `last_place_id` (`lat`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `email`, `password`, `lat`, `long`) VALUES
(1, 'mohamed', 'mhmdsamir@gmail.comusers', '123', 30.0310036, 31.2127736),
(2, 'mohamed', 'mhmdsamir1@gmail.com', '123', 0, NULL),
(3, 'mohamed', 'mhmdsamir91@gmail.com', '123456789', NULL, NULL),
(4, 'mohamed', 'mhmdsamir92@gmail.com', '123456789', NULL, NULL),
(5, 'mohamed', 'm.samir', '123456789', 30, 31),
(6, 'Omar', 'omar', '123', NULL, NULL);


--
-- This table handle the followers
--
CREATE TABLE IF NOT EXISTS `follower` (
  `personid` int(11) NOT NULL,
  `targetid` int(11) NOT NULL,
  PRIMARY KEY (`personid`, `targetid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;

alter TABLE `follower` add foreign key(`personid`) references users(`id`)

select  * from users

insert into `follower` values(1,2)

select * from follower

SET SQL_SAFE_UPDATES=0;
DELETE FROM follower WHERE `personid`= 1 and `targetid` = 2

SET SQL_SAFE_UPDATES=1;

SELECT `lat` , `long` FROM users WHERE `id` = 1

DELETE FROM follower WHERE 'personid'= ? and 'targetid' = ?
