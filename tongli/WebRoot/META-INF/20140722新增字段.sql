ALTER TABLE `tc_course_web_enroll`
ADD COLUMN `disease_note`  varchar(500) NULL COMMENT '是否重大疾病' AFTER `course`,
ADD COLUMN `allergy_note`  varchar(500) NULL COMMENT '是否食物过敏' AFTER `disease_note`;

