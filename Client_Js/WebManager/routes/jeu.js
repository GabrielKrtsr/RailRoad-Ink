import express from 'express';
var jeuRouter = express.Router();

import { sendHTMLfile } from '../controllers/jeuController.js';

jeuRouter.get('/', sendHTMLfile);

export {jeuRouter}

