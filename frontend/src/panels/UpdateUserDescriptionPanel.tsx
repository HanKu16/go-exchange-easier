import { useState } from 'react'
import Box from '@mui/material/Box'
import { IconButton, Tooltip } from '@mui/material'
import { Button } from '@mui/material'
import { sendUpdateDescriptionRequest } from '../utils/user'
import Alert from '@mui/material/Alert'
import { TextField } from '@mui/material'
import InfoIcon from '@mui/icons-material/Info'
import type { AlertMessage } from '../types/AlertMessage'
import PanelHeader from '../components/PanelHeader'

const UpdateUserDescriptionPanel = () => {
  const maxDescriptionSize = 500
  const [description, setDescription] = useState<string>('')
  const [message, setMessage] = useState<AlertMessage | null>(null)

  const handleDescriptionUpdate = async () => {
    setMessage({type: 'info', content: 'Waiting for server response.'})
    const result = await sendUpdateDescriptionRequest({description: description})
    if (result.isSuccess) {
      setMessage({type: 'success', content: 'Description was updated successfully.'})
    } else {
      if (result.error.fieldErrors.some(e => e.code === "Size")) {
        setMessage({type: 'error', content: `Description can not be longer 
          than ${maxDescriptionSize} characters. But current has 
          ${description.length}.`})
      } else {
        setMessage({type: 'error', content: 'Failed to update description. Please try again later.'})
      }
    }
  }

  return (
    <Box>
      <Box sx={{display: 'flex', alignItems: 'center'}}>
        <PanelHeader label='Update your description'/>
        <Tooltip title={`Description can not be longer than ${maxDescriptionSize} characters 
          (currently ${description.length}).`} sx={{paddingBottom: 3}}>
          <IconButton>
            <InfoIcon/>
          </IconButton>
        </Tooltip>
      </Box>

      {message != null ?
        <Alert variant='filled' severity={message.type} sx={{marginY: 2}}>
          {message.content}
        </Alert> : 
        <></>
      }
      <TextField id='description-input' label='Description' multiline rows={6}
          placeholder='Enter your description' sx={{width: '100%', marginTop: 2}}
          value={description} onChange={event => setDescription(event.target.value)}/>
      <Button variant='contained' size='large' sx={{marginTop: 2}}
          onClick={handleDescriptionUpdate}>
          CONFIRM
      </Button>
    </Box>
  )
}

export default UpdateUserDescriptionPanel