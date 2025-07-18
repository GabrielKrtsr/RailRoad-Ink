import express from 'express';
var partieRouter = express.Router();

import { sendHTMLfile } from '../controllers/partieController.js';

partieRouter.get('/', sendHTMLfile);

export {partieRouter}

