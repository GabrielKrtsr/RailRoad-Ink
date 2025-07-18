import express from 'express';
var indexRouter = express.Router();

import { sendHTMLfile } from '../controllers/indexController.js';

indexRouter.get('/', sendHTMLfile);

export {indexRouter}

